package rain.dsys.common.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 文件操作通用类
 *
 * @author Joe
 */
public class FileUtil {

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
        return Files.list(Paths.get(path)).filter(FileUtil::checkDir)
                .map(Path::getFileName).map(Path::toString).mapToInt(version -> Integer.parseInt(version)).max().getAsInt();
    }

}
