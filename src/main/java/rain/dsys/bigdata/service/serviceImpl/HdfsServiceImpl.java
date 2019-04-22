package rain.dsys.bigdata.service.serviceImpl;

import org.springframework.stereotype.Service;
import rain.dsys.bigdata.service.HdfsService;

@Service
public class HdfsServiceImpl implements HdfsService {

    private static Configuration conf = null;

    private static String PRINCIPAL_NAME = DynamicConfigUtil.getProperty("user");

    private static String KEYTAB_PATH = DynamicConfigUtil.getProperty("keytab");

    private static String KRB5_CONF_PATH = DynamicConfigUtil.getProperty("krbPath");

    private static void authentication() {
      if ("kerberos".equalsIgnoreCase(conf.get("hadoop.security.authentication"))) {
        try {
          LoginUtil.login(PRINCIPAL_NAME, KEYTAB_PATH, KRB5_CONF_PATH, conf);
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    
    private static void confLoad() {
      if (conf == null) {
        conf = new Configuration();
      }
    }
    
    @Override
    public String index() {
        return "hdfs service...";
    }

    @Override
    public void uploadBigdata() {
      confLoad();
      authentication();

      UserGroupInformation ugi = UserGroupInformation.createProxyUser(userName, UserGroupInformation.getLoginUser());
      ugi.doAs(new PrivilegedExceptionAction<Void>() {
        @Override
        public Void run() {
          try {
            HdfsUtil.read(conf, "/user/joe/test.txt");
          } catch (IOException e) {
            e.printStackTrace();
          }
          return null;
        }
      });
    }
}
