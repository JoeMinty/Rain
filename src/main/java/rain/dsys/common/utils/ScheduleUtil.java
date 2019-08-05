import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class ScheduleUtil {

    private static final int POOL_SIZE = 100;

    private static ScheduledThreadPoolExecutor scheduleExecutor = null;

    private static final long INITIAL_DELAY = 0L;

    static {
        scheduleExecutor = new ScheduledThreadPoolExecutor(POOL_SIZE);
    }

    public static synchronized void addTask(Runnable task, long period) {
        scheduleExecutor.scheduleAtFixedRate(task, INITIAL_DELAY, period, TimeUnit.SECONDS);
        return;
    }

    public static synchronized void addOnceTask(Runnable task) {
        scheduleExecutor.execute(task);
        return;
    }
}
