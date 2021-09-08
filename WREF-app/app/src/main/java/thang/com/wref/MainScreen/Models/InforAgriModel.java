package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class InforAgriModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("idTheme")
    private String idTheme;
    @SerializedName("title")
    private String title;
    @SerializedName("url")
    private String url;
    @SerializedName("image")
    private String image;
    @SerializedName("created_at")
    private long created_at;

    @Override
    public String toString() {
        return "InforAgriModel{" +
                "id='" + id + '\'' +
                ", idTheme=" + idTheme +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                ", image='" + image + '\'' +
                ", created_at=" + created_at +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdTheme() {
        return idTheme;
    }

    public void setIdTheme(String idTheme) {
        this.idTheme = idTheme;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public InforAgriModel(String id, String idTheme, String title, String url, String image, long created_at) {
        this.id = id;
        this.idTheme = idTheme;
        this.title = title;
        this.url = url;
        this.image = image;
        this.created_at = created_at;
    }
}
