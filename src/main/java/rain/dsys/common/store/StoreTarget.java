package rain.dsys.common.store;

public interface StoreTarget {

    void storeTargetDataset(Object obj);

    void storeTargetModel(Object obj);

    void storeTargetElse(Object obj);

}
