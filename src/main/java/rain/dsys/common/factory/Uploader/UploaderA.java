package rain.dsys.common.factory.Uploader;

public class UploaderA implements Uploader{

    @Override
    public void upload() {
        System.out.println("uploader a");
    }

    public static Uploader newInstance() {
        return new UploaderA();
    }
}
