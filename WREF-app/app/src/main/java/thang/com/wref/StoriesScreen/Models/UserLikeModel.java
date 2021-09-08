package thang.com.wref.StoriesScreen.Models;

import com.google.gson.annotations.SerializedName;

public class UserLikeModel {
    @SerializedName("iduserLike")
    private UsersModel iduserLike;

    public UserLikeModel(UsersModel iduserLike) {
        this.iduserLike = iduserLike;
    }

    @Override
    public String toString() {
        return "UserLikeModel{" +
                "iduserLike=" + iduserLike +
                '}';
    }

    public UsersModel getIduserLike() {
        return iduserLike;
    }

    public void setIduserLike(UsersModel iduserLike) {
        this.iduserLike = iduserLike;
    }
}
