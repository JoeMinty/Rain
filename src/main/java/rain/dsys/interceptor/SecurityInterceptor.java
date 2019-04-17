import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author Joe
 *
 * 配置拦截器过滤所有用户认证
 */
public class SecurityInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    String abc = "aaaa";
    // 验证权限
    if (StringUtils.equals("aaaa", abc)) {
      return true;
    }

    response.reset();
    //设置编码格式
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");

    PrintWriter pw = response.getWriter();

    JSONObject json = new JSONObject(true);
    json.put("msg", "用户不存在");

    pw.write(json.toString());
    pw.flush();
    pw.close();
    return false;
  }


}
