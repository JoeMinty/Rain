package rain.dsys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;



@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class RainApplication {

	public static void main(String[] args) {
		SpringApplication.run(RainApplication.class, args);
	}

}

