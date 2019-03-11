package rain.dsys.common.store;

import org.apache.commons.lang3.StringUtils;
import sun.rmi.transport.ObjectTable;

public class StoreImpl extends StoreFactory {


    private String type;

    public StoreImpl(String type) {
        this.type = type;
    }

    @Override
    public void storeTargetDataset(Object obj) {
        storeMedium(obj);
    }

    @Override
    public void storeTargetModel(Object obj) {
        storeMedium(obj);

    }

    @Override
    public void storeTargetElse(Object obj) {

        storeMedium(obj);

    }

    @Override
    public void storeMedium(Object obj) {
        if (StringUtils.equals("nfs", this.type)) {
            storeNfs(obj);
        } else {
            storeHdfs(obj);
        }
    }

    private void storeNfs(Object obj) {
        System.out.println("store nfs ,target type is " + obj.toString());
    }

    private void storeHdfs(Object obj) {
        System.out.println("store hdfs ,target type is " + obj.toString());
    }
 }
