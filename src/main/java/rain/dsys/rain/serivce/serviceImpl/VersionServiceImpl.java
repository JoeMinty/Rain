package rain.dsys.rain.serivce.serviceImpl;

import org.springframework.stereotype.Service;
import rain.dsys.bigdata.utils.RepositoryVersionUtil;
import rain.dsys.rain.constant.VersionConstant;
import rain.dsys.rain.serivce.VersionService;

import java.io.File;
import java.io.IOException;

@Service
public class VersionServiceImpl implements VersionService {

    @Override
    public void genVersion(String name) {
        try {
            String path = VersionConstant.VERSION_PREFIX + File.separator + name;
            int versionId = RepositoryVersionUtil.getMaxVersion(path);

            // 将上个版本的origin目录复制到本地，针对此目录进行操作

            // 生成新的本地仓库

            RepositoryVersionUtil.genLocalNewRepository(path, ++versionId);
            System.out.println("local repository version is " + versionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new VersionServiceImpl().genVersion("onepiece");
    }
}
