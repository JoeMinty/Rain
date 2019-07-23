
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * http 工具类
 *
 * @author Joe
 */
@Log4j2
public class HttpUtil {

  private static final String CHARSET_UTF_8 = "UTF-8";

  private HttpUtil() {
    throw new IllegalStateException("Utility class");
  }

  public static JSONObject httpRestClient(String url, JSONObject postData) {
    SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
    requestFactory.setConnectTimeout(10 * 1000);
    requestFactory.setReadTimeout(10 * 1000);
    RestTemplate client = new RestTemplate(requestFactory);
    return client.postForEntity(url, postData, JSONObject.class).getBody();
  }

  /**
   * http get
   * @param url url
   * @return response
   */
  public static String get(String url) {
    log.info(url.trim());

    HttpGet httpGet = new HttpGet(url.trim());
    RequestConfig requestConfig = RequestConfig.custom()
      .setConnectTimeout(30000)
      .setConnectionRequestTimeout(30000)
      .setSocketTimeout(5000)
      .build();
    httpGet.setConfig(requestConfig);

    try {
      HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpGet);
      return EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF_8);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return "get error";
  }

  /**
   * post 请求
   * @param url   url
   * @param param 参数
   * @return      response
   */
  public static String post(String url, String param) {
    log.info(url.trim());
    log.info(param);

    HttpPost httpPost = new HttpPost(url.trim());
    RequestConfig requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(30000)
            .setConnectionRequestTimeout(30000)
            .setSocketTimeout(30000)
            .build();
    httpPost.setConfig(requestConfig);

    StringEntity se = new StringEntity(param, CHARSET_UTF_8);
    httpPost.setEntity(se);

    try {
      HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);
      return EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF_8);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return "post error";
  }

  /**
   * 带表单的post请求
   * @param url     url
   * @param params  参数列表
   * @return        response
   */
  public static String postForm(String url, Map<String, String> params) {

    HttpPost httpPost = new HttpPost(url.trim());
    RequestConfig requestConfig =
        RequestConfig.custom()
            .setConnectTimeout(30000)
            .setConnectionRequestTimeout(30000)
            .setSocketTimeout(30000)
            .build();
    httpPost.setConfig(requestConfig);

    List<NameValuePair> form = new ArrayList<>();
    for (Map.Entry<String, String> entry : params.entrySet()) {
      form.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
    }

    try {
      UrlEncodedFormEntity entity = new UrlEncodedFormEntity(form, CHARSET_UTF_8);
      httpPost.setEntity(entity);
      HttpResponse httpResponse = HttpClientBuilder.create().build().execute(httpPost);
      return EntityUtils.toString(httpResponse.getEntity(), CHARSET_UTF_8);
    } catch (Exception e) {
      log.error(e.getMessage());
    }

    return "post form error";
  }
}
