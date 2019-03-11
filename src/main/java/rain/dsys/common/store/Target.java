package rain.dsys.common.store;

public class Target implements StoreTarget {
    @Override
    public void storeTargetDataset() {
        System.out.println("dataset");
    }

    @Override
    public void storeTargetModel() {
        System.out.println("model");
    }
}
