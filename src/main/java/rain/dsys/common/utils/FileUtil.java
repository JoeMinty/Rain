package rain.dsys.common.utils;

/**
 * 文件工具类
 *
 * @author Joe
 */
public class FileUtil {

   public static boolean breakPoinkCheck(int chunks, int chunk, String uploadFile) throws IOException {
    String fileName = "aa";
    File tempFile = new File(uploadFile, fileName + ".conf");
    RandomAccessFile accessConfFile = null;
    byte isComplete = Byte.MAX_VALUE;
    try {
      accessConfFile = new RandomAccessFile(tempFile, "rw");
      if (accessConfFile.length() == 0)
        accessConfFile.setLength(chunks);

      accessConfFile.seek(chunk);
      accessConfFile.write(Byte.MAX_VALUE);
      byte[] completeList = FileUtils.readFileToByteArray(tempFile);
      for (int i = 0; i < completeList.length && isComplete == Byte.MAX_VALUE; i++) {
        isComplete = (byte) (isComplete & completeList[i]);
      }
    } finally {
      if (accessConfFile != null) {
        accessConfFile.close();
      }
    }

    return isComplete == Byte.MAX_VALUE ? true : false;
  }
}
