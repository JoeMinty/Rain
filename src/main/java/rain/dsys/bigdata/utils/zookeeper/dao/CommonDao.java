package rain.dsys.bigdata.utils.zookeeper.dao;

public interface CommonDao {
	public void create(String path, String value);

	public String getNodeData(String path);

	public void deleteNode(String path);

	public void updateNode(String path, String value);
}
