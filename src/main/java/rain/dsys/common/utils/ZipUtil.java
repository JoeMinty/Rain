package rain.dsys.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 通用处理类
 *
 * @author Joe
 */

public class ZipUtil {


    private static void zip(ZipOutputStream out, Path path, String parentPath) throws IOException {
        File file = path.toFile();

        if (file.isFile()) {

            // input file
            try (FileInputStream in = new FileInputStream(file);) {

                out.putNextEntry(new ZipEntry(parentPath));

                // buffer size
                byte[] b = new byte[1024];
                int count;

                while ((count = in.read(b)) > 0) {
                    out.write(b, 0, count);
                }
                out.closeEntry();
            }


        }

        if (file.isDirectory()) {

            if (file.listFiles().length == 0) {
                out.putNextEntry(new ZipEntry(StringUtils.equals("", parentPath) ? file.getName() : parentPath + File.separator + file.getName()));
            }

            for (File sub : file.listFiles()) {
                zip(out, sub.toPath(), StringUtils.equals("", parentPath) ? sub.getName() : parentPath + File.separator + sub.getName());
            }

        }
    }


    /**
     * zip打包(自动区分文件和目录)
     *
     * @param source      资源名
     * @param destination zip名
     */
    public static void zip(String source, String destination) throws IOException {
        Path path = Paths.get(source);
        // output file
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destination));
        zip(out, path, "");
        out.close();
    }


    /**
     * 解压到当前路径
     */
    public static void unZip(File srcFile, String destDirPath) throws RuntimeException {



    }


    /**
     * 解压zip包到具体路径
     */
    public static void unzip(String destination) {

    }

    public static void main(String[] args) throws IOException{
        zip("/Users/lulinfeng/images", "/Users/lulinfeng/hello.zip");
    }

}
