package thang.com.wref.MainScreen.Weather;

import com.google.gson.annotations.SerializedName;

public class weather {
    @SerializedName("id")
    private int id;
    @SerializedName("main")
    private String main;
    @SerializedName("description")
    private String description;
    @SerializedName("icon")
    private String iconn;

    public weather(int id, String main, String description, String iconn) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.iconn = iconn;
    }

    @Override
    public String toString() {
        return "weather{" +
                "id=" + id +
                ", main='" + main + '\'' +
                ", description='" + description + '\'' +
                ", iconn='" + iconn + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIconn() {
        return iconn;
    }

    public void setIconn(String iconn) {
        this.iconn = iconn;
    }
}
