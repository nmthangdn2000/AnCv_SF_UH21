package thang.com.wref.ChatBot;

import com.google.gson.JsonObject;

public class MessageModel {
    private int viewType;
    private String type;
    private String message;
    private JsonObject data;
    private String entity;
    private String oldIntent;
    private int repeat;

    public MessageModel(int viewType, String type, String message, JsonObject data, String entity, String oldIntent, int repeat) {
        this.viewType = viewType;
        this.type = type;
        this.message = message;
        this.data = data;
        this.entity = entity;
        this.oldIntent = oldIntent;
        this.repeat = repeat;
    }

    @Override
    public String toString() {
        return "MessageModel{" +
                "viewType=" + viewType +
                ", type='" + type + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", entity='" + entity + '\'' +
                ", oldIntent='" + oldIntent + '\'' +
                ", repeat=" + repeat +
                '}';
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject getData() {
        return data;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getOldIntent() {
        return oldIntent;
    }

    public void setOldIntent(String oldIntent) {
        this.oldIntent = oldIntent;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
