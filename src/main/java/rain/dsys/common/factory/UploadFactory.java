package rain.dsys.common.factory;

import rain.dsys.common.factory.Uploader.Uploader;
import rain.dsys.common.factory.Uploader.UploaderA;
import rain.dsys.common.factory.Uploader.UploaderB;

public class UploadFactory {

    private String type;

    public UploadFactory setType(String type) {
        this.type = type;
        return this;
    }

    public Uploader build() {
        Uploader uploader = null;
        if ("a".equals(this.type)) {
            uploader = createA();
        } else {
            uploader = createB();
        }
        return uploader;
    }

    private Uploader createA() {
        return UploaderA.newInstance();
    }

    private Uploader createB() {
        return UploaderB.newInstance();
    }

}
