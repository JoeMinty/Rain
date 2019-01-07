package rain.dsys.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 通过反射获取springboot工程容器中的bean
 *
 * @author Joe
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (ApplicationContextUtil.applicationContext == null) {
            ApplicationContextUtil.applicationContext = applicationContext;
        }
    }

    public static Object getBean(String className) {
        return applicationContext.getBean(className);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static <T> T  getBean(String className, Class<T> clazz) {
        return applicationContext.getBean(className, clazz);
    }

}