package rain.dsys.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

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
    private void invoke(Class<?> clazz, String method, Object... args) throws InvocationTargetException, IllegalAccessException, InstantiationException {
        Method[] methods = clazz.getMethods();

        for (Method met : methods) {
            if (StringUtils.equals(method, met.getName()) && met.getParameterCount() == args.length) {

                // 正确调用方法
                met.invoke(clazz.newInstance(), args);
                break;
            }
        }
    }
}
