package rain.dsys.common.quartz;

import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Component;

/**
 * 监听类
 * 
 * @author Allen
 */
@Component
public class SchedulerListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	AScheduler myScheduler;

	@Autowired
	JobFactory myJobFactory;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try {
			myScheduler.schedulerJob();
			System.out.println("SynchronizedData job  start...");
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	@Bean(name = "mySchedulerFactoryBean")
	public SchedulerFactoryBean mySchedulerFactory() {
		SchedulerFactoryBean bean = new SchedulerFactoryBean();
		bean.setOverwriteExistingJobs(true);
		bean.setStartupDelay(1);
		bean.setJobFactory(myJobFactory);
		return bean;
	}
}
