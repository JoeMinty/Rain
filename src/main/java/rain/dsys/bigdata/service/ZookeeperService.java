package rain.dsys.bigdata.service;

import org.springframework.stereotype.Service;

@Service
public interface ZookeeperService {

    String zookeeperTest();

    void register(String serviceName, String serviceAddress);
}
