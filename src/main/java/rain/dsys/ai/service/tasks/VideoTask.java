package rain.dsys.ai.service.tasks;

import org.springframework.stereotype.Component;

@Component
public class VideoTask implements Task{

    @Override
    public void doWork() {
        System.out.println("video task");
    }

    @Override
    public void doWork(String message) {
        System.out.println("video task === message is" + message);
    }
}
