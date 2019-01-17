package rain.dsys.bigdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rain.dsys.bigdata.service.HdfsService;

/**
 * hdfs rest 控制层
 *
 * @author Joe
 */
@RestController
public class HdfsController {

    @Autowired
    private HdfsService hdfsService;

    @RequestMapping("/hdfs")
    public String index() {
        return hdfsService.index();
    }


}
