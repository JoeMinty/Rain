package rain.dsys.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.dsys.common.ReflectUtil;

@RestController
public class TaskController {

    @Autowired
    private ReflectUtil reflectUtil;

    @RequestMapping("/task")
    public String index() throws Exception{

        reflectUtil.run("rain.dsys.ai.service.tasks.ImageTask", "doWork", "image");
        reflectUtil.run("rain.dsys.ai.service.tasks.VideoTask", "doWork", "video");

        return "ai task";
    }
}
