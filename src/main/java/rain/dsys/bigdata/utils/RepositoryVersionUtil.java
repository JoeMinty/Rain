package rain.dsys.bigdata.utils;

import com.alibaba.druid.VERSION;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 版本仓库操作通用类
 *
 * @author Joe
 */
public class RepositoryVersionUtil {

    /**
     * 检查是否是目录
     */
    public static boolean checkDir(Path path) {
        return Files.isDirectory(path);
    }


    /**
     * 返回目录中对应最大的版本
     */
    public static Integer getMaxVersion(String path) throws IOException {
        return Files.list(Paths.get(path)).filter(RepositoryVersionUtil::checkDir)
                .map(Path::getFileName).map(Path::toString).mapToInt(version -> Integer.parseInt(version)).max().getAsInt();
    }


    /**
     * 生成本地仓库新版本目录
     *
     * @param versionId  本地仓库版本号
     */
    public static void genLocalRepository(int versionId) {
        // 上版本解压出来的目录

        // 当前版本所做的增删改操作目录
    }

}
