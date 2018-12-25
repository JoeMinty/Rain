package rain.dsys.bigdata.service.serviceImpl;

import org.springframework.stereotype.Service;
import rain.dsys.bigdata.service.SparkService;

@Service
public class SparkServiceImpl implements SparkService {

    @Override
    public String sparkTest() {
        System.out.print("spark test...");
        return "spark";
    }

}
