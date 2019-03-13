package rain.dsys.common.store;

import org.springframework.stereotype.Service;

@Service
public class StoreImpl extends StoreFactory {

    @Override
    public void storeTargetDataset(Object obj) {
        System.out.println("store dateset ... ");
    }

    @Override
    public void storeTargetModel(Object obj) {
        System.out.println("store model ... ");

    }

    @Override
    public void storeTargetElse(Object obj) {
        System.out.println("store else ... ");

    }


 }
