package thang.com.wref.MainScreen.Models;

public class DataPaging<T> {
    private T data;

    public DataPaging(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DataPaging{" +
                "data=" + data +
                '}';
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
