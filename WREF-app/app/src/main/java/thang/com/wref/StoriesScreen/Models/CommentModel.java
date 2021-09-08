package thang.com.wref.StoriesScreen.Models;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class CommentModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("idUser")
    private UsersModel idUser;
    @SerializedName("idPosts")
    private NewsModels idPosts;
    @SerializedName("_id")
    private CommentModel idComment;
    @SerializedName("content")
    private String content;
    @SerializedName("media")
    private String media;
    @SerializedName("Like")
    private UserLikeModel[] Like;
    @SerializedName("create_at")
    private int create_at;
    @SerializedName("update_at")
    private int update_at;

    public CommentModel(String id, UsersModel idUser, NewsModels idPosts, CommentModel idComment, String content, String media, UserLikeModel[] like, int create_at, int update_at) {
        this.id = id;
        this.idUser = idUser;
        this.idPosts = idPosts;
        this.idComment = idComment;
        this.content = content;
        this.media = media;
        Like = like;
        this.create_at = create_at;
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "id='" + id + '\'' +
                ", idUser=" + idUser +
                ", idPosts=" + idPosts +
                ", idComment=" + idComment +
                ", content='" + content + '\'' +
                ", media='" + media + '\'' +
                ", Like=" + Arrays.toString(Like) +
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

    public NewsModels getIdPosts() {
        return idPosts;
    }

    public void setIdPosts(NewsModels idPosts) {
        this.idPosts = idPosts;
    }

    public CommentModel getIdComment() {
        return idComment;
    }

    public void setIdComment(CommentModel idComment) {
        this.idComment = idComment;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public UserLikeModel[] getLike() {
        return Like;
    }

    public void setLike(UserLikeModel[] like) {
        Like = like;
    }

    public int getCreate_at() {
        return create_at;
    }

    public void setCreate_at(int create_at) {
        this.create_at = create_at;
    }

    public int getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(int update_at) {
        this.update_at = update_at;
    }
}
