package rain.dsys.bigdata.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 版本仓库操作通用类
 *
 * @author Joe
 */
public class RepositoryVersionUtil {

    private static final String ADD_DIR = "add";

    /**
     * 检查是否是目录
     */
    public static boolean checkDir(Path path) {
        return Files.isDirectory(path);
    }


    /**
     * 利用正则表达式判断字符串是否是数字
     */
    private static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }


    /**
     * 检查本地仓库目录是否符合目录结构
     */
    public static boolean checkRepositoryDir(Path path) {
        return isNumeric(path.toFile().getName());
    }


    /**
     * 返回目录中对应最大的版本
     */
    public static Integer getMaxVersion(String path) throws IOException {
        return Files.list(Paths.get(path)).filter(RepositoryVersionUtil::checkDir).filter(RepositoryVersionUtil::checkRepositoryDir)
                .map(Path::getFileName).map(Path::toString).mapToInt(version -> Integer.parseInt(version)).max().getAsInt();
    }


    /**
     * 生成本地仓库新版本目录
     *
     * @param versionId  本地仓库版本号
     */
    public static void genLocalNewRepository(String path, int versionId){
        String versionPath = path + File.separator + versionId + File.separator + ADD_DIR;
        System.out.println("new " + versionPath);
        try {
            Files.createDirectories(Paths.get(versionPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成当前版本的zip包目录，需要合并实体的crud操作
     *
     */
    public static void genRepositoryOrigin() {
        // 上版本的zip解压出来的目录

        // 当前版本所做的增删改操作目录
    }

}
