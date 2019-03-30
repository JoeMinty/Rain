package rain.dsys.bigdata.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.fs.permission.FsPermission;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

/**
 * @author Joe
 */
public class HdfsUtils {

    private HdfsUtils() {
    }

    private static final Configuration CONF = new Configuration();

    static {
        CONF.setBoolean("fs.hdfs.impl.disable.cache", true);
    }

    public static void writeFile(String root, String currentPath, byte[] data, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        FSDataOutputStream fs = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(root);

            if (!fileSystem.exists(fsPath)) {
                fileSystem.mkdirs(fsPath);
            }

            Path filePath = new Path(root + File.separator + currentPath);
            fs = fileSystem.create(filePath, true);
            fs.write(data);
            fs.hsync();
            // 十进制511 == 八进制777
            fileSystem.setPermission(filePath, new FsPermission((short) 511));
        } finally {
            closeStreamAndFileSystem(fileSystem, fs, null);
        }
    }

    public static byte[] readFile(String path, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        FSDataInputStream fs = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);

            if (!fileSystem.exists(fsPath)) {
                throw new IOException("Path:" + path + " not exist");
            }
            fs = fileSystem.open(fsPath);
            int fileLen = (int) fileSystem.getFileStatus(fsPath).getLen();
            byte[] data = new byte[fileLen];
            int dataLength = fs.read(data);
            byte[] schemaData = new byte[dataLength];
            System.arraycopy(data, 0, schemaData, 0, dataLength);
            return schemaData;

        } finally {
            closeStreamAndFileSystem(fileSystem, null, fs);
        }
    }

    // 读取文件全部内容
    public static byte[] readFullFile(String path, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        FSDataInputStream fs = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);

            if (!fileSystem.exists(fsPath)) {
                throw new IOException("Path:" + path + " not exist");
            }
            fs = fileSystem.open(fsPath);
            int fileLen = (int) fileSystem.getFileStatus(fsPath).getLen();
            byte[] data = new byte[fileLen];
            fs.readFully(0, data);
            return data;
        } finally {
            closeStreamAndFileSystem(fileSystem, null, fs);
        }
    }

    public static void deleteFile(String path, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);
            if (fileSystem.exists(fsPath)) {
                fileSystem.delete(fsPath, true);
            }

        } finally {
            closeFileSystem(fileSystem);
        }
    }

    public static void downloadFile(String hdfsFileWithPath, String basePath, String localPath)
            throws URISyntaxException, IOException {

        FileSystem localFS = null;
        FileSystem hadoopFS = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            localFS = FileSystem.getLocal(CONF);
            hadoopFS = FileSystem.get(CONF);
            Path hdfsPath = new Path(hdfsFileWithPath);
            Path local = new Path(localPath);

            hadoopFS.copyToLocalFile(false, hdfsPath, local, true);
        } finally {
            closeFileSystem(hadoopFS);
            closeFileSystem(localFS);
        }
    }

    public static void uploadFile(String hdfsPathName, String basePath, String localFileWithPath)
            throws URISyntaxException, IOException {

        FileSystem localFS = null;
        FileSystem hadoopFS = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            localFS = FileSystem.getLocal(CONF);
            hadoopFS = FileSystem.get(CONF);
            Path hdfsPath = new Path(hdfsPathName);
            Path localPath = new Path(localFileWithPath);

            if (!hadoopFS.exists(hdfsPath)) {
                hadoopFS.mkdirs(hdfsPath);
            }

            hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
        } finally {
            closeFileSystem(hadoopFS);
            closeFileSystem(localFS);
        }
    }

    /**
     * 仅将本地目录下的文件或子目录上传到指定目录
     *
     * @param hdfsPathName      上传到hdfs的路径
     * @param basePath          hdfs地址
     * @param localFileWithPath 本地指定目录
     */
    public static void uploadFiles(String hdfsPathName, String basePath, String localFileWithPath)
            throws URISyntaxException, IOException {

        FileSystem hadoopFS = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            hadoopFS = FileSystem.get(CONF);
            Path hdfsPath = new Path(hdfsPathName);
            Path localPath;

            if (!hadoopFS.exists(hdfsPath)) {
                hadoopFS.mkdirs(hdfsPath);
            }

            File localDir = new File(localFileWithPath);
            String[] subRes = localDir.list();
            if (null == subRes) {
                return;
            }
            for (String file : subRes) {
                localPath = new Path(localFileWithPath + File.separator + file);
                hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
            }
        } finally {
            closeFileSystem(hadoopFS);
        }
    }

    private static Set<String> traverseTable(String path, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);

            FileStatus[] fileStatuses = fileSystem.listStatus(fsPath);

            Set<String> tableNames = new HashSet<>();

            for (FileStatus fs : fileStatuses) {
                tableNames.add(fs.getPath().getName());
            }

            return tableNames;
        } finally {
            closeFileSystem(fileSystem);
        }
    }

    public static void isExistOrCreate(String path, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);

            if (!fileSystem.exists(fsPath)) {
                fileSystem.mkdirs(fsPath);
            }

            fileSystem.setPermission(fsPath, new FsPermission((short) 511));
        } finally {
            closeFileSystem(fileSystem);
        }
    }

    public static boolean isFileExists(String root, String currentPath, String basePath)
            throws URISyntaxException, IOException {
        FileSystem fileSystem = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);

            Path filePath = new Path(root + File.separator + currentPath);

            return fileSystem.exists(filePath);
        } finally {
            closeFileSystem(fileSystem);
        }
    }

    /**
     * @param path     文件夹路径
     * @param basePath hdfs原始路径
     * @return 返回二级文件下下的所有文件，形式为<一级文件夹名字， 二级文件名1，二级文件名2，···， 二级文件名n>
     */
    public static Map<String, String> listFiles(String path, String basePath)
            throws URISyntaxException, IOException {
        Set<String> folderNames = traverseTable(path, basePath);
        Map<String, String> folder2Files = new HashMap<>();
        for (String folderName : folderNames) {
            StringBuilder sFile = new StringBuilder();
            Set<String> files = traverseTable(path + "/" + folderName, basePath);
            for (String file : files) {
                sFile.append(file).append(",");
            }
            // 如果文件夹中没有文件，则不加入map
            if (sFile.length() != 0) {
                folder2Files.put(folderName, sFile.substring(0, sFile.length() - 1));
            }
        }

        return folder2Files;
    }

    public static boolean appendToFile(String filePath, String basePath, String value)
            throws URISyntaxException, IOException {
        Configuration conf = new Configuration();
        conf.setBoolean("dfs.support.append", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        conf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
        FileSystem fileSystem = null;
        FSDataOutputStream outPutStream = null;
        try {
            FileSystem.setDefaultUri(conf, new URI(basePath));
            fileSystem = FileSystem.get(conf);
            outPutStream = fileSystem.append(new Path(filePath));
          //  outPutStream.write(BytesUtils.toBytes(value));
            outPutStream.flush();

        } finally {
            closeStreamAndFileSystem(fileSystem, outPutStream, null);
        }
        return true;
    }

    public static List<String> getFiles(String path, String basePath)
            throws IOException, URISyntaxException {
        FileSystem fileSystem = null;
        List<String> files = new ArrayList<>();
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            fileSystem = FileSystem.get(CONF);
            Path fsPath = new Path(path);

            FileStatus[] fileStatuses = fileSystem.globStatus(fsPath);

            for (FileStatus fs : fileStatuses) {
                if (fileSystem.exists(fs.getPath())) {
                    files.add(fs.getPath().getName());
                }
            }

            return files;
        } finally {
            closeFileSystem(fileSystem);
        }
    }

    /**
     * 本地目录下的文件或子目录上传到指定目录
     *
     * @param hdfsPathName      上传到hdfs的路径
     * @param basePath          hdfs地址
     * @param localFileWithPath 本地指定目录
     */
    public static void uploadFilesWithWildcard(
            String hdfsPathName, String basePath, String localFileWithPath, String wildcard)
            throws URISyntaxException, IOException {

        FileSystem hadoopFS = null;
        try {
            FileSystem.setDefaultUri(CONF, new URI(basePath));
            hadoopFS = FileSystem.get(CONF);
            Path hdfsPath = new Path(hdfsPathName);
            Path localPath;

            if (!hadoopFS.exists(hdfsPath)) {
                hadoopFS.mkdirs(hdfsPath);
            }

            File localDir = new File(localFileWithPath);
            String[] subRes = localDir.list();
            if (null == subRes) {
                return;
            }
            for (String file : subRes) {
                if (file.startsWith(wildcard)) {
                    localPath = new Path(localFileWithPath + File.separator + file);
                    hadoopFS.copyFromLocalFile(false, true, localPath, hdfsPath);
                }
            }
        } finally {
            closeFileSystem(hadoopFS);
        }
    }

    private static void closeFileSystem(FileSystem fs) {
        try {
            if (null != fs) {
                fs.close();
            }
        } catch (IOException e) {
//            log.error("close filesystem failed.", e);
        }
    }

    private static void closeStreamAndFileSystem(
            FileSystem fs, FSDataOutputStream outPutStream, FSDataInputStream inputStream) {
        try {
            if (null != outPutStream) {
                outPutStream.close();
            }
            if (null != fs) {
                fs.close();
            }
            if (null != inputStream) {
                inputStream.close();
            }
        } catch (IOException e) {
 //           log.error("close filesystem or outputStream failed.", e);
        }
    }
}
