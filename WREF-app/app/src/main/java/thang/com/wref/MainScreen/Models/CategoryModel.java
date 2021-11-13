package thang.com.wref.MainScreen.Models;

import com.google.gson.annotations.SerializedName;

public class CategoryModel {
    @SerializedName("_id")
    private String id;
    private String name;
    private String slug;

    public CategoryModel(String id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }

    @Override
    public String toString() {
        return "CategoryModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", slug='" + slug + '\'' +
                '}';
    }

    public String getId() {
        return id;
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
}
