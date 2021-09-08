package thang.com.wref.MainScreen.Models;

public class CropYieldModel {
    private String title;
    private int icon;
    private String more1;
    private String more2;
    private int icon2;
    private String background;

    public CropYieldModel(String title, int icon, String more1, String more2, int icon2, String background) {
        this.title = title;
        this.icon = icon;
        this.more1 = more1;
        this.more2 = more2;
        this.icon2 = icon2;
        this.background = background;
    }

    @Override
    public String toString() {
        return "CropYieldModel{" +
                "title='" + title + '\'' +
                ", icon=" + icon +
                ", more1='" + more1 + '\'' +
                ", more2='" + more2 + '\'' +
                ", icon2=" + icon2 +
                ", background='" + background + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMore1() {
        return more1;
    }

    public void setMore1(String more1) {
        this.more1 = more1;
    }

    public String getMore2() {
        return more2;
    }

    public void setMore2(String more2) {
        this.more2 = more2;
    }

    public int getIcon2() {
        return icon2;
    }

    public void setIcon2(int icon2) {
        this.icon2 = icon2;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
