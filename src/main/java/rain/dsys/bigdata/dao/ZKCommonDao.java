package rain.dsys.bigdata.dao;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;


/**
 * ZK操作工具类
 * 
 * @author Allen
 */
public class ZKCommonDao {

	CuratorFramework curatorFramework = null;

	public ZKCommonDao(CuratorFramework curatorFramework) {
		this.curatorFramework = curatorFramework;
	}

	/**
	 * 默认创建持久化无序节点
	 * 
	 * @param path
	 *            节点路径
	 * @param value
	 *            节点数据
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void create(String path, String value) {
		this.create(path, value, false);
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点路径
	 * @param value
	 *            节点数据
	 * @param isTemp
	 *            是否是临时节点
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void create(String path, String value, Boolean isTemp) {
		this.create(path, value, isTemp, false);
	}

	/**
	 * 创建节点
	 * 
	 * @param path
	 *            节点路径
	 * @param value
	 *            节点数据
	 * @param isTemp
	 *            是否是临时节点
	 * @param isOrder
	 *            是否是有序节点
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void create(String path, String value, Boolean isTemp, Boolean isOrder) {
		try {
			CreateMode text = null;
			if (!isOrder) {
				if (!isTemp) {
					text = CreateMode.PERSISTENT;
				} else {
					text = CreateMode.EPHEMERAL;
				}
			} else {
				if (!isTemp) {
					text = CreateMode.PERSISTENT_SEQUENTIAL;
				} else {
					text = CreateMode.EPHEMERAL_SEQUENTIAL;
				}

			}
			curatorFramework.create().creatingParentsIfNeeded().withMode(text).forPath(path, value.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 根据节点路径获取对应节点的数据
	 * 
	 * @param path
	 *            节点路径
	 * @return
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public String getNodeData(String path) {
		Stat stat = new Stat();
		String result = "";
		byte[] bs;
		try {
			bs = curatorFramework.getData().storingStatIn(stat).forPath(path);
			result = new String(bs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 删除节点
	 * 
	 * @param path
	 *            节点路径
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void deleteNode(String path) {
		try {
			curatorFramework.delete().deletingChildrenIfNeeded().forPath(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 更新节点数据
	 * 
	 * @param path
	 *            节点路径
	 * @param value
	 *            节点数据
	 * @exception/throws [违例类型] [违例说明]
	 * @see [类、类#方法、类#成员]
	 */
	public void updateNode(String path, String value) {
		try {
			curatorFramework.setData().forPath(path, value.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
