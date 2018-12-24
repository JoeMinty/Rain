package rain.dsys.bigdata.service.serviceImpl;

import org.apache.zookeeper.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import rain.dsys.bigdata.service.ZookeeperService;

import java.util.concurrent.CountDownLatch;

@Service
public class ZookeeperImpl implements ZookeeperService, Watcher {

    private static final String REGISTRY_PATH = "/registry";

    private static final int SESSION_TIMEOUT = 5000;

    private static CountDownLatch latch = new CountDownLatch(1);

    private ZooKeeper zk;

    public ZookeeperImpl(String zkServers) {
        try {
            zk = new ZooKeeper(zkServers, SESSION_TIMEOUT, this);
            latch.await();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    @RequestMapping(value = "/zktest", method = RequestMethod.GET)
    public String zookeeperTest() {

        Watcher watcher = new Watcher() {
            public void process(WatchedEvent event) {
                System.out.println("receive event：" + event);
            }
        };

        String value = null;
        try {
            // 这里测试docker部署的zk服务
            final ZooKeeper zookeeper = new ZooKeeper("127.0.0.1:2181", SESSION_TIMEOUT, watcher);
            final byte[] data = zookeeper.getData("/zknode-test", watcher, null);
            value = new String(data);
            zookeeper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "get value from zookeeper node [" + value + "]";

    }

    /**
     * 服务注册
     *
     * @param serviceName
     * @param serviceAddress
     */
    @Override
    public void register(String serviceName, String serviceAddress) {
        String registryPath = REGISTRY_PATH;
        try {

            //创建根节点：持久节点
            if (zk.exists(registryPath, false) == null) {
                zk.create(registryPath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE,
                        CreateMode.PERSISTENT);

            }
            //创建服务节点：持久节点
            String servicePath = registryPath + "/" + serviceName;
            if (zk.exists(servicePath, false) == null) {
                zk.create(servicePath, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);

            }
            //创建地址节点：临时顺序节点
            String addressPath = servicePath + "/address-";
            String addressNode = zk.create(addressPath, serviceAddress.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

        } catch (Exception e) {

        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
            latch.countDown();
        }
    }
}
