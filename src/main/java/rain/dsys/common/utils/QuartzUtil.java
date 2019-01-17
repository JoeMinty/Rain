package rain.dsys.common.utils;

import java.util.UUID;

import io.netty.util.internal.StringUtil;
import rain.dsys.common.quartz.QuartzManager;

/**
 * Quartz工具类
 * 
 * @author Allen
 *
 * todo 调整下结构，util都放在utils包下
 */
public class QuartzUtil {

	// 如果不传参的话为QuartzUtil创建一个默认的job
	public static String JOB_NAME = "动态任务调度";
	public static String TRIGGER_NAME = "动态任务触发器";
	public static String JOB_GROUP_NAME = "XLXXCC_JOB_GROUP";
	public static String TRIGGER_GROUP_NAME = "XLXXCC_JOB_GROUP";

	public QuartzUtil() {
		this(JOB_NAME, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
	}

	public QuartzUtil(String jOB_NAME2) {
		this(jOB_NAME2, UUID.randomUUID().toString(), UUID.randomUUID().toString(), UUID.randomUUID().toString());
	}

	public QuartzUtil(String jOB_NAME3, String tRIGGER_NAME3, String jOB_GROUP_NAME3, String tRIGGER_GROUP_NAME3) {
		if (StringUtil.isNullOrEmpty(jOB_NAME3)) {
			throw new NullPointerException("JOB_NAME 为空");
		}
		if (StringUtil.isNullOrEmpty(tRIGGER_NAME3)) {
			throw new NullPointerException("tRIGGER_NAME 为空");
		}
		if (StringUtil.isNullOrEmpty(jOB_GROUP_NAME3)) {
			throw new NullPointerException("jOB_GROUP_NAME 为空");
		}
		if (StringUtil.isNullOrEmpty(tRIGGER_GROUP_NAME3)) {
			throw new NullPointerException("tRIGGER_GROUP_NAME 为空");
		}
		this.JOB_NAME = jOB_NAME3;
		this.TRIGGER_NAME = tRIGGER_NAME3;
		this.JOB_GROUP_NAME = jOB_GROUP_NAME3;
		this.TRIGGER_GROUP_NAME = tRIGGER_GROUP_NAME3;
	}

	/**
	 * 代表每多少秒循环一次
	 * 
	 * @param jobClass
	 *            job的class
	 * @param second
	 *            秒数
	 */
	public void addJob(Class jobClass, int second) {
		this.addJob(JOB_NAME, jobClass, second);
	}

	public void addJob(String jOB_NAME2, Class jobClass, int second) {
		String hashName = jOB_NAME2 + getHash(jOB_NAME2);
		this.addJob(jOB_NAME2, hashName, hashName, hashName, jobClass, second);
	}

	public void addJob(String jOB_NAME2, String TRIGGER_NAME, String JOB_GROUP_NAME, String TRIGGER_GROUP_NAME,
			Class jobClass, int second) {
		QuartzManager.addJob(jOB_NAME2, TRIGGER_NAME, JOB_GROUP_NAME, TRIGGER_GROUP_NAME, jobClass,
				"0/" + second + " * * * * ?");
	}

	/**
	 * 删除job
	 */
	public void removeJob() {
		this.removeJob(JOB_NAME);
	}

	public void removeJob(String JOB_NAME) {
		String hashName = JOB_NAME + getHash(JOB_NAME);
		this.removeJob(JOB_NAME, hashName, hashName, hashName);
	}

	public void removeJob(String jOB_NAME, String jOB_GROUP_NAME, String tRIGGER_NAME, String tRIGGER_GROUP_NAME) {
		QuartzManager.removeJob(jOB_NAME, jOB_GROUP_NAME, tRIGGER_NAME, tRIGGER_GROUP_NAME);
	}

	/**
	 * 修改job的轮询秒数
	 * 
	 * @param second
	 */
	public void update(int second) {
		this.updatebyName(JOB_NAME, second);
	}

	public void updatebyName(String JobName, int second) {
		String hashName = JobName + getHash(JobName);
		this.updatebyName(JobName, hashName, hashName, hashName, second);
	}

	public void updatebyName(String jobName, String jOB_GROUP_NAME, String tRIGGER_NAME, String tRIGGER_GROUP_NAME,
			int second) {
		QuartzManager.modifyJobTime(jobName, jOB_GROUP_NAME, tRIGGER_NAME, tRIGGER_GROUP_NAME,
				"0/" + second + " * * * * ?");
	}

	public int getHash(String JobName) {
		int m = 1 << 10 - 1;
		return hash(JobName.hashCode()) & m;
	}

	/**
	 * 自定义hash算法
	 * 
	 * @param hashCode
	 * @return
	 */
	private int hash(int hashCode) {
		hashCode = hashCode ^ (hashCode >>> 12) ^ (hashCode >>> 8);
		return hashCode ^ (hashCode >>> 7) ^ (hashCode >>> 5);
	}
}
