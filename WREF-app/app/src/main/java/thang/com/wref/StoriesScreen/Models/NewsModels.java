package thang.com.wref.StoriesScreen.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

import thang.com.wref.MainScreen.Models.LocationModel;

public class NewsModels {
    @SerializedName("_id")
    private String id;
    @SerializedName("idUser")
    private UsersModel idUser;
    @SerializedName("idLocation")
    private LocationModel idLocation;
    @SerializedName("content")
    private String content;
    @SerializedName("media")
    private String[] media;
//    @SerializedName("Like")
//    private UserLikeModel[] Like;
    @SerializedName("Comment")
    private int Comment;
    @SerializedName("create_at")
    private long create_at;
    @SerializedName("update_at")
    private long update_at;

    public NewsModels(String id, UsersModel idUser, LocationModel idLocation, String content, String[] media, int comment, long create_at, long update_at) {
        this.id = id;
        this.idUser = idUser;
        this.idLocation = idLocation;
        this.content = content;
        this.media = media;
        Comment = comment;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return "NewsModels{" +
                "id='" + id + '\'' +
                ", idUser=" + idUser +
                ", idLocation=" + idLocation +
                ", content='" + content + '\'' +
                ", media=" + Arrays.toString(media) +
                ", Comment=" + Comment +
                ", create_at=" + create_at +
                ", update_at=" + update_at +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UsersModel getIdUser() {
        return idUser;
    }

    public void setIdUser(UsersModel idUser) {
        this.idUser = idUser;
    }

    public LocationModel getIdLocation() {
        return idLocation;
    }

    public void setIdLocation(LocationModel idLocation) {
        this.idLocation = idLocation;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getMedia() {
        return media;
    }

    public void setMedia(String[] media) {
        this.media = media;
    }

    public int getComment() {
        return Comment;
    }

    public void setComment(int comment) {
        Comment = comment;
    }

    public long getCreate_at() {
        return create_at;
    }

    public void setCreate_at(long create_at) {
        this.create_at = create_at;
    }

    public long getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(long update_at) {
        this.update_at = update_at;
    }
}
