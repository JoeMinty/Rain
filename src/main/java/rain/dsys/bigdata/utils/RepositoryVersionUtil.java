package rain.dsys.bigdata.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.FileUtils;
import rain.dsys.common.utils.ZipUtil;

import java.io.*;
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
    public static  Integer getMaxVersion(String path) throws IOException {
        return Files.list(Paths.get(path)).filter(RepositoryVersionUtil::checkDir).filter(RepositoryVersionUtil::checkRepositoryDir)
                .map(Path::getFileName).map(Path::toString).mapToInt(version -> Integer.parseInt(version)).max().getAsInt();
    }


    /**
     * 生成本地仓库新版本目录
     *
     */
    public static synchronized void genLocalNewRepository(String path){
        System.out.println("new " + path);
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 
     *
     * @param srcPath
     * @param desPath
     */
    public static synchronized void genRepository(String preVersionPath, String addPath, String srcPath, String desPath) throws IOException{
        genRepositoryOrigin(preVersionPath, addPath, srcPath, desPath);
        dealAddDir();
        dealDel();
    }


    /**
     * 生成当前版本的zip包目录，需要合并实体的crud操作
     */
    public static void genRepositoryOrigin(String preVersionPath, String addPath, String srcPath, String desPath) throws IOException{

        String zipName = preVersionPath + File.separator + "hello.zip";
        if (ZipUtil.checkZip(zipName)) {
            // 上版本的zip解压出来的目录
            System.out.println("直接拷贝zip");
        } else {
            // 直接处理origin目录
            FileUtils.copyDirectory(new File(srcPath), new File(desPath));
        }

        // 当前版本所做的增删改操作目录

        FileUtils.copyDirectory(new File(addPath), new File(desPath));
    }


    /**
     * 处理add目录中的文件
     */
    public static void dealAddDir() {
        System.out.println("处理add目录中的文件");
    }


    /**
     * 处理删除的文件
     */
    private static void dealDel(){
        System.out.println("处理删除的文件");
        // 处理日志文件中的每一行记录，后续改成数据库持久化
        JSONObject json = new JSONObject(true);

        try (BufferedReader fis = new BufferedReader(new FileReader("/Users/lulinfeng/datasets/tmp.log"))){
            String line = "";
            while ((line = fis.readLine()) != null) {
                if (line.startsWith("del")) {
                    System.out.println(line);
                }
            }

        } catch (IOException e) {

        }

    }

    public static void main(String[] args) {
        dealDel();
    }
}
