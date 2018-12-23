package rain.dsys.bigdata.service.serviceImpl;

import org.springframework.stereotype.Service;
import rain.dsys.bigdata.service.HdfsService;

@Service
public class HdfsServiceImpl implements HdfsService {

    @Override
    public String index() {
        return "hdfs service...";
    }
}
