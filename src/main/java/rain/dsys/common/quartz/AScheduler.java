package rain.dsys.common.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdScheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import rain.dsys.common.QuartzJobs.MyJob1;
import rain.dsys.common.QuartzJobs.MyJob2;
/**
 * 添加job,方便扩展job
 * 
 * @author Allen
 */
@Component
public class AScheduler {
	public void schedulerJob() throws SchedulerException {
		ApplicationContext annotationContext = SpringUtil.getApplicationContext();
		StdScheduler stdScheduler = (StdScheduler) annotationContext.getBean("mySchedulerFactoryBean");//获得上面创建的bean
		Scheduler myScheduler =stdScheduler;
		startScheduler1(myScheduler);
		startScheduler2(myScheduler);
		myScheduler.start();
	}
 
	public void startScheduler1(Scheduler scheduler) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(MyJob1.class).withIdentity("job1", "jobGroup1").build();
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/5 * * * * ?");
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "triggerGroup1")
				.withSchedule(cronScheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
	
	public void startScheduler2(Scheduler scheduler) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(MyJob2.class).withIdentity("job2", "jobGroup2").build();
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger2", "triggerGroup2")
				.withSchedule(cronScheduleBuilder).build();
		scheduler.scheduleJob(jobDetail, trigger);
	}
}
