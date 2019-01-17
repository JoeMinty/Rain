package rain.dsys;

import java.util.concurrent.TimeUnit;

import rain.dsys.common.QuartzJobs.MyJob2;
import rain.dsys.common.utils.QuartzUtil;

public class TestQuartz {

	public static void main(String[] args) throws InterruptedException {
		// 1)初始化工具类Joe
		QuartzUtil q = new QuartzUtil();
		// 2)新增俩个job,并且启动【建议采取第一种写法，命名job名称，名字冲突会报错，后者的写法只能使用一次】
		// q.addJob("JOE",MyJob1.class, 3);
		q.addJob(MyJob2.class, 5);
		TimeUnit.SECONDS.sleep(10);
		// 3）修改job轮询时间
		// q.updatebyName("JOE", 1);
		q.update(1);
		TimeUnit.SECONDS.sleep(10);
		System.out.println(">>>>>>>>>>>>>>>>>");
		// 4)删除job
		// q.removeJob("JOE");
		q.removeJob();
	}
}
