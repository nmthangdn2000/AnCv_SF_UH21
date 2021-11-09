package thang.com.wref.Commerce;

import java.util.ArrayList;

import thang.com.wref.MainScreen.Models.ProductModel;

public class ItemLayoutModel {
    private String title;
    private ArrayList<ProductModel> products;

    public ItemLayoutModel(String title, ArrayList<ProductModel> products) {
        this.title = title;
        this.products = products;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<ProductModel> getProductModel() {
        return products;
    }

    public void setProductModel(ArrayList<ProductModel> products) {
        this.products = products;
    }
}
