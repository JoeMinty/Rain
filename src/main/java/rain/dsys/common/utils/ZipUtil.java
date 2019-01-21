package rain.dsys.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * zip 通用处理类
 *
 * @author Joe
 */

public class ZipUtil {

    /**
     * 将某个目录打包成zip
     *
     * @param directory 目录名
     */
    public static void createZip(String directory) throws IOException {

        File file = new File(directory);
        File[] files = file.listFiles();

        // input file
        FileInputStream in = new FileInputStream(file);

        // out put file
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(new File(file, "file.jar")));

        // name the file inside the zip  file
        out.putNextEntry(new ZipEntry(files[0].getName()));

        // buffer size
        byte[] b = new byte[1024];
        int count;

        while ((count = in.read(b)) > 0) {
            System.out.println();
            out.write(b, 0, count);
        }
        out.close();
        in.close();

    }

    public static void unzip() {

    }


    /**
     * 解压zip包到具体路径
     */
    public static void unzip(String dest) {

    }

}
