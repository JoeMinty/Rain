package rain.dsys.interceptor.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * 用户测试Controller
 * 
 * @author Allen
 */
@RestController
@RequestMapping("/Rain")
public class UserController {
	
	@RequestMapping("/test")
    public void test(HttpServletResponse response) throws IOException {
        response.getWriter().print("<h1>Hello interceptor test</h1>");
    }
}
