package rain.dsys.common.config;

import lombok.Data;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Joe
 */
@Data
@Component
@PropertySource(value = "classpath:config.properties")
public class CommonConfig {

  private String[] paths;

}
