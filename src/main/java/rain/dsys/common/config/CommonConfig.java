import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author Joe
 */
@Data
@Component
@PropertySource(value = "classpath:config.properties")
public class NfsConfiguration {

  private String[] paths;

}
