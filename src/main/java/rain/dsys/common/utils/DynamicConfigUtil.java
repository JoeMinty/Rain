package rain.dsys.common.utils;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * @author Joe
 * 动态读取配置文件
 */
public class DynamicConfigUtil {

  private static PropertiesConfiguration propsConfig;

  private static final String COMMON_PATH = "/opt/joe/test/common.properties";

  static {

    try {
      propsConfig =
        new PropertiesConfiguration(COMMON_PATH);
      propsConfig.setReloadingStrategy(new FileChangedReloadingStrategy());
      propsConfig.setEncoding("UTF-8");
    } catch (ConfigurationException e) {
      e.printStackTrace();
    }
  }

  /** 获取数组，以，切分 */
  public static String[] getArrayProperty(String key) {
    return propsConfig.getStringArray(key);
  }

  public static String getProperty(String key) {
    return propsConfig.getString(key);
  }
  
}
