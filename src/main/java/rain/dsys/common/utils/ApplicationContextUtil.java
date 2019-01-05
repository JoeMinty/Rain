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
        if (this.applicationContext == null) {
            this.applicationContext = applicationContext;
        }
    }

}