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
}
