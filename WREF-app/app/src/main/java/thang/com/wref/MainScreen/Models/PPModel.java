package thang.com.wref.MainScreen.Models;

public class PPModel {
    private String amount;
    private String time;
    private int icon;
    private String rate;
    private String borderColor;

    public PPModel(String amount, String time, int icon, String rate, String borderColor) {
        this.amount = amount;
        this.time = time;
        this.icon = icon;
        this.rate = rate;
        this.borderColor = borderColor;
    }

    @Override
    public String toString() {
        return "PPModel{" +
                "amount='" + amount + '\'' +
                ", time='" + time + '\'' +
                ", icon=" + icon +
                ", rate='" + rate + '\'' +
                ", borderColor='" + borderColor + '\'' +
                '}';
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }
}
