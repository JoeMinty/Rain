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
public class MyJob1 implements Job {

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		JobKey key = jobExecutionContext.getJobDetail().getKey();
		JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();
		System.out.println("key : " + key);
	}
}
