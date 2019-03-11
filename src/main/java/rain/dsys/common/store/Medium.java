package rain.dsys.common.store;

public class Medium implements StoreMedium {
    @Override
    public void storeMediumNfs() {
        System.out.println("nfs");
    }

    @Override
    public void storeMediumHdfs() {
        System.out.println("hdfs");
    }
}
