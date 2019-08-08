package rain.dsys.common.utils;

import java.io.IOException;
import java.util.Properties;

/**
 * 配置项工具类
 *
 * @author Joe
 */
public class ConfigUtil {
  private static Properties properties;

  static {
    properties = new Properties();
    try {
      properties.load(
        Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getProperty(String key) {
    return properties.getProperty(key);
  }
  
  public static Properties getProperitiesInfos(final ClassLoader classLoader, final String fileName) {
        if (classLoader == null || StringUtils.isBlank(fileName)) {
            LOG.error("Fail to get properities, Args is invalid: classLoader={}, fileName={}", classLoader, fileName);
            return null;
        }

        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            LOG.error("Fail to get properities, fail to get resource as stream");
            return null;
        }

        try {
            Properties prop = new Properties();
            prop.load(inputStream);
            return prop;
        } catch (IOException e) {
            LOG.error("Fail to get properities, ", e);
        } finally {
            IOUtils.closeStream(inputStream);
        }

        return null;
    }
  
  public static void main(String[] args {
    getProperitiesInfos(this.getClass().getClassLoader(), "applications-dev.properties");
  }

}
