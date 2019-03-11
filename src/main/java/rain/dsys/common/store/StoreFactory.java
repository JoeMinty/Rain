package rain.dsys.common.store;

public abstract class StoreFactory implements  {

    StoreMedium createStoreMedium();

    StoreTarget createStoreTarget();
}
