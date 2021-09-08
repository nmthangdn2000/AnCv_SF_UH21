package thang.com.wref.StoriesScreen.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class StoriesModels {
    @SerializedName("_id")
    private String id;
    @SerializedName("idUser")
    private UsersModel users;
    @SerializedName("media")
    private String[] media;
    @SerializedName("createdAt")
    private String createdAt;

    public StoriesModels(String id, UsersModel users, String[] media, String createdAt) {
        this.id = id;
        this.users = users;
        this.media = media;
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StoriesModels{" +
                "id='" + id + '\'' +
                ", users=" + users +
                ", media=" + Arrays.toString(media) +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsersModel getUsers() {
        return users;
    }

    public void setUsers(UsersModel users) {
        this.users = users;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
