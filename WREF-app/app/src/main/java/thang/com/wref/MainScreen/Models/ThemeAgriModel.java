package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class ThemeAgriModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("slug")
    private String slug;

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "ThemeAgriModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public ThemeAgriModel(String id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }
}
