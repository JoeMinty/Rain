package rain.dsys.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 扩展类添加自定义拦截器
 * 
 * @author Allen
 */
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Autowired
	private CustomConfig customconfig;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//添加自定义拦截器及拦截规则
		registry.addInterceptor(customconfig).addPathPatterns("/**");
		super.addInterceptors(registry);
	}
}
