package rain.dsys.bigdata.utils.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 客戶端连接ZK工具类
 * 
 * @author Allen
 */
public class ZKClientUtils {

	private static CuratorFramework curatorFramework;

	// 默认的ZK连接地址
	private final static String CONNECTSTRING = "192.168.48.128:2181,192.168.48.130:2181,"
			+ "192.168.48.131:2181,192.168.48.132:2181";

	// 会话过期时间
	private static int sessionTimeoutMs = 5000;

	// 连接超时时间
	private static int connectionTimeoutMs = 5000;

	// 俩次连接等待时间
	private static int baseSleepTimeMs = 1000;

	// 最大尝试连接次数
	private static int maxRetries = 3;

	public static CuratorFramework getInstance() {
		return getInstance(CONNECTSTRING);
	}

	public static CuratorFramework getInstance(String CONNECTSTRING) {
		return getInstance(CONNECTSTRING, sessionTimeoutMs, connectionTimeoutMs);
	}

	public static CuratorFramework getInstance(String CONNECTSTRING, int sessionTimeoutMs, int connectionTimeoutMs) {
		return getInstance(CONNECTSTRING, sessionTimeoutMs, connectionTimeoutMs, baseSleepTimeMs, maxRetries);
	}

	public static CuratorFramework getInstance(String CONNECTSTRING, int sessionTimeoutMs, int connectionTimeoutMs,
			int baseSleepTimeMs, int maxRetries) {
		curatorFramework = CuratorFrameworkFactory.newClient(CONNECTSTRING, sessionTimeoutMs, sessionTimeoutMs,
				new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries));
		curatorFramework.start(); // start方法启动连接
		return curatorFramework;
	}
}
