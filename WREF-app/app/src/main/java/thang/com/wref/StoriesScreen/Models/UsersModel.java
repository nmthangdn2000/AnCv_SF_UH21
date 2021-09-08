package thang.com.wref.StoriesScreen.Models;

import com.google.gson.annotations.SerializedName;

public class UsersModel {
    @SerializedName("_id")
    private String id;
    @SerializedName("userName")
    private String username;
    @SerializedName("email")
    private String email;
    @SerializedName("passWord")
    private String password;
    @SerializedName("avata")
    private String avata;
    @SerializedName("token")
    private String token;

    public UsersModel(String id, String username, String email, String password, String avata, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avata = avata;
        this.token = token;
    }

    @Override
    public String toString() {
        return "UsersModel{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", avata='" + avata + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvata() {
        return avata;
    }

    public void setAvata(String avata) {
        this.avata = avata;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
