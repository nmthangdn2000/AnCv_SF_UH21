package thang.com.wref.MainScreen.Havest;

public class PhotoModel {
    private int img;

    public PhotoModel(int img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "PhotoModel{" +
                "img=" + img +
                '}';
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
