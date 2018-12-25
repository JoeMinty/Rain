package rain.dsys.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.dsys.bigdata.service.SparkService;

@RestController
public class SparkController {

    @Autowired
    private SparkService sparkService;

    @RequestMapping("/spark")
    public String index() {
        return sparkService.sparkTest();
    }
}
