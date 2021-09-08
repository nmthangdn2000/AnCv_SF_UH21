package thang.com.wref.MainScreen.Models;

public class AgriModel {
    private String id;
    private String image;
    private String title;

    public AgriModel(String id, String image, String title) {
        this.id = id;
        this.image = image;
        this.title = title;
    }

    @Override
    public String toString() {
        return "AgriModel{" +
                "id='" + id + '\'' +
                ", image='" + image + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
