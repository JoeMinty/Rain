package rain.dsys.bigdata.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

/**
 * hdfs 工具类
 *
 * @author Joe
 */
public class HdfsUtil {


    /**
     * 上传本地文件到hdfs
     */
    public static void copyFile(Configuration conf , String uri , String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        fs.close();
    }


    /**
     * 获取hdfs上的文件流
     */
    public static  void getFileStream(Configuration conf , String uri , String local, String remote) throws IOException{
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        Path path= new Path(remote);
        FSDataInputStream in = fs.open(path);
        FileOutputStream fos = new FileOutputStream(local);
        int ch = 0;
        while((ch=in.read()) != -1){
            fos.write(ch);
        }
        in.close();
        fos.close();
    }


    /**
     * 下载hdfs上的文件到本地
     */
    public static void download(Configuration conf , String uri ,String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.copyToLocalFile(path, new Path(local));
        fs.close();
    }


    /**
     * 删除hdfs上文件或者文件夹
     */
    public static void delete(Configuration conf , String uri,String filePath) throws IOException {
        Path path = new Path(filePath);
        FileSystem fs = FileSystem.get(URI.create(uri), conf);
        fs.deleteOnExit(path);
        fs.close();
    }



}
