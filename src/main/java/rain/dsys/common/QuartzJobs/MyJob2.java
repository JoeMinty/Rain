package rain.dsys.common.QuartzJobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
/**
 * 测试job
 * @author lulf
 */
public class MyJob2 implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
//		String name = dataMap.getString("Name");
//		int age = dataMap.getInt("Age");
		System.out.println("key : " + key);
//		System.out.println("Name : " + name);
//		System.out.println("Age : " + age);
//		System.out.println(new Date() + ": doing something...");
	}
}
