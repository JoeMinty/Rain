package rain.dsys.rain.serivce.serviceImpl;

import org.springframework.stereotype.Service;
import rain.dsys.bigdata.utils.RepositoryVersionUtil;
import rain.dsys.rain.serivce.VersionService;

import java.io.File;
import java.io.IOException;

@Service
public class VersionServiceImpl implements VersionService {

    private static final String VERSION_PREFIX = "/Users/lulinfeng/datasets";

    private static final String ADD_DIR = "add";

    private static final String ORIGIN_DIR = "origin";

    @Override
    public void genVersion(String name) {
        try {
            String path = VERSION_PREFIX + File.separator + name;
            int versionId = RepositoryVersionUtil.getMaxVersion(path);

            // 将上个版本的origin目录复制到本地，针对此目录进行操作
            genLocalRepository(path, versionId);

            // 生成新的本地仓库
            String localNewRepositoryPath = path + File.separator + (++versionId) + File.separator + ADD_DIR;
            RepositoryVersionUtil.genLocalNewRepository(localNewRepositoryPath);
            System.out.println("local repository version is " + versionId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成本地仓库
     */
    private static void genLocalRepository(String path, int versionId) throws IOException{
        String desPath = path + File.separator + versionId + File.separator + ORIGIN_DIR;
        String srcPath = path + File.separator + (--versionId) + File.separator + ORIGIN_DIR;
        RepositoryVersionUtil.genRepository(srcPath, desPath);
    }

    public static void main(String[] args) {
        new VersionServiceImpl().genVersion("onepiece");

    }
}
