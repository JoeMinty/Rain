package rain.dsys.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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


    private static void zip(ZipOutputStream out, Path path, String parentPath) throws IOException{
        File file = path.toFile();

        if (file.isFile()) {

            // input file
            try (FileInputStream in = new FileInputStream(file);){

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

           for (File sub: file.listFiles()) {
               zip(out, sub.toPath(), StringUtils.equals("", parentPath) ? file.getName() : parentPath + File.separator + sub.getName());
           }

        }
    }


    /**
     * 打包zip
     *
     * @param source       资源名
     * @param destination  zip名
     */
    public static void zip(String source, String destination) throws IOException {
        Path path = Paths.get(source);

        // output file
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(destination));
        zip(out, path, "");
        out.close();
    }


    public static void unzip() {

    }


    /**
     * 解压zip包到具体路径
     */
    public static void unzip(String dest) {

    }

}
