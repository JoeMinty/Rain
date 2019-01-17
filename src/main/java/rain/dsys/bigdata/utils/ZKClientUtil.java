package rain.dsys.bigdata.utils;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 客戶端连接ZK工具类
 * 
 * @author Allen
 */
public class ZKClientUtil {

	private static CuratorFramework curatorFramework;

	// 默认的ZK连接地址
	private final static String CONNECT_STRING = "192.168.48.128:2181,192.168.48.130:2181,"
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
		return getInstance(CONNECT_STRING);
	}

	public static CuratorFramework getInstance(String connectStr) {
		return getInstance(connectStr, sessionTimeoutMs, connectionTimeoutMs);
	}

	public static CuratorFramework getInstance(String connectStr, int sessionTimeoutMs, int connectionTimeoutMs) {
		return getInstance(connectStr, sessionTimeoutMs, connectionTimeoutMs, baseSleepTimeMs, maxRetries);
	}

	public static CuratorFramework getInstance(String connectStr, int sessionTimeoutMs, int connectionTimeoutMs,
			int baseSleepTimeMs, int maxRetries) {
		curatorFramework = CuratorFrameworkFactory.newClient(connectStr, sessionTimeoutMs, sessionTimeoutMs, new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries));

		// start方法启动连接
		curatorFramework.start();
		return curatorFramework;
	}
}
