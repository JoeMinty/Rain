package rain.dsys.common.factory.Uploader;

public class UploaderB implements Uploader{

    @Override
    public void upload() {
        System.out.println("uploader b");
    }

    public static Uploader newInstance() {
        return new UploaderB();
    }
}
