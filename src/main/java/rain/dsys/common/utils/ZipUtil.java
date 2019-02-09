package rain.dsys.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * zip 通用处理类
 *
 * @author Joe
 */

public class ZipUtil {

    private static final Long THRESHOLD_SIZE = 1024L * 1024L;


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
     * 解压zip包
     */
    public static void unZip(String resDir, String destDir) throws IOException {

        File dest = new File(destDir);

        byte[] buff = new byte[1024];

        ZipInputStream zis = new ZipInputStream(new FileInputStream(resDir));
        ZipEntry zipEntry;
        while((zipEntry = zis.getNextEntry()) != null) {
            File file = new File(dest, zipEntry.getName());

            if (!file.getParentFile().exists() && !file.getParentFile().mkdirs()) {
                throw new IOException("directory is not exists");
            }

            if (zipEntry.isDirectory()) {
                file.mkdir();
            } else {
                try (FileOutputStream fos = new FileOutputStream(file)){
                    int count;

                    while ((count = zis.read(buff)) > 0) {
                        fos.write(buff, 0, count);
                    }
                }

            }
        }

    }


    /**
     * 检查文件是否为大文件
     *
     * @return
     */
    public static boolean checkZip(String path) throws IOException {
        System.out.println("zip path ===" + path);
        System.out.println("size === " + Files.size(Paths.get(path)));
        if (Files.size(Paths.get(path)) > THRESHOLD_SIZE) {
            return true;
        }

        return false;
    }


    public static void main(String[] args) throws IOException{
//        zip("/Users/joe/images", "/Users/joe/hello.zip");
//        unZip("/Users/joe/aa/hello.zip", "/Users/joe/aa/images");
    }

}
