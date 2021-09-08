package thang.com.wref.LoginScreen.Models;

import com.google.gson.annotations.SerializedName;

public class ErrModel {
    @SerializedName("success")
    private boolean success;
    @SerializedName("msg")
    private String msg;

    public ErrModel(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "ErrModel{" +
                "success=" + success +
                ", msg='" + msg + '\'' +
                '}';
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}