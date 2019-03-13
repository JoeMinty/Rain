package rain.dsys.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.dsys.common.ReflectUtil;
import rain.dsys.common.store.StoreImpl;

@RestController
public class TaskController {

    @Autowired
    private ReflectUtil reflectUtil;

    @Autowired
    private StoreImpl storeImpl;


    @RequestMapping("/task")
    public String index() throws Exception{

        reflectUtil.run("rain.dsys.ai.service.tasks.ImageTask", "doWork", "image");
        reflectUtil.run("rain.dsys.ai.service.tasks.VideoTask", "doWork", "video");

        return "ai task";
    }

    @RequestMapping("/store")
    public String store() {
        storeImpl.storeTargetDataset("");
        storeImpl.storeTargetModel("");
        storeImpl.storeTargetElse("");
        return "store";
    }
}
