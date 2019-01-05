package rain.dsys.ai.service.tasks;

import org.springframework.stereotype.Component;

@Component
public class ImageTask implements Task{

    @Override
    public void doWork(String message) {
        System.out.println("image task === message is" + message);
    }
}