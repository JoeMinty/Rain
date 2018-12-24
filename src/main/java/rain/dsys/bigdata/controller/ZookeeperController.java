package rain.dsys.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.dsys.bigdata.service.ZookeeperService;

@RestController
public class ZookeeperController {

    @Autowired
    private ZookeeperService zookeeperService;

    @RequestMapping("/zookeeper")
    public String index() {
        return zookeeperService.zookeeperTest();
    }
}
