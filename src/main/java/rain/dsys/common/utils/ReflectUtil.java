package rain.dsys.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import rain.dsys.common.utils.ApplicationContextUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 统一反射类
 *
 * @author Joe
 */
@Component
public class ReflectUtil {

    /**
     * 入口函数
     */
    public void run(String className, String method, Object... args) throws Exception {
        try {
            invoke(Class.forName(className), method, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * springboot 根据类名，方法名，参数反射调用正确的方法
     */
    private void invoke(Class<?> cla, String method, Object... args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Object clazz = ApplicationContextUtil.getBean(cla);
        Method[] methods = clazz.getClass().getMethods();

        for (Method met : methods) {
            if (StringUtils.equals(method, met.getName()) && met.getParameterCount() == args.length) {

                // 正确调用方法
                // met.invoke(clazz.newInstance(), args);
                met.invoke(clazz, args);
                break;
            }
        }
    }
}
