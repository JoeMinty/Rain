package rain.dsys.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import rain.dsys.common.utils.ApplicationContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
public class ReflectUtil {

    public void run(String className, String method, Object...args) throws Exception{
//        Class<?> clazz = ApplicationContextUtil.getBean(className);
        Class clazz = Class.forName(className);
        try {
            invoke(clazz, method, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    private void invoke(Class<?> clazz, String method, Object...args) throws InvocationTargetException, IllegalAccessException {
        Method[] methods = clazz.getClass().getMethods();

        for (Method met : methods) {
            if (StringUtils.equals(method, met.getName())) {

                if (null == args) {
                    met.invoke(clazz);
                } else if (met.getParameterCount() == args.length) {
                    met.invoke(clazz, args);
                }

                break;
            }
        }
    }
}
