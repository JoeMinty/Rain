package rain.dsys.common.config;

import org.apache.catalina.connector.Connector;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {
  @Bean
  public ServletWebServerFactory servletContainer() {
      TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
          @Override
          protected void postProcessContext(Context context) {
              SecurityConstraint securityConstraint = new SecurityConstraint();
              securityConstraint.setUserConstraint("CONFIDENTIAL");
              SecurityCollection collection = new SecurityCollection();
              collection.addPattern("/*");
              securityConstraint.addCollection(collection);
              context.addConstraint(securityConstraint);
          }
      };
      tomcat.addAdditionalTomcatConnectors(redirectConnector());
      return tomcat;
  }

  private Connector redirectConnector() {
      Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
      connector.setScheme("http");
      connector.setPort(8080);
      connector.setSecure(false);
      connector.setRedirectPort(8443);
      return connector;
  }
}
