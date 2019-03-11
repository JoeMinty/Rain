package rain.dsys.common.store;

public class Main {

    public static void main(String [] args) {
        StoreImpl ss = new StoreImpl("nfs");
        ss.storeTargetDataset("ds");
        ss.storeTargetModel("model");
        ss.storeTargetElse("else");



        StoreImpl ss1 = new StoreImpl("hdfs");
        ss1.storeTargetDataset("ds");
        ss1.storeTargetModel("model");
        ss1.storeTargetElse("else");

    }
}
