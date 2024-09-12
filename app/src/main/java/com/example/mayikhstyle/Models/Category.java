package com.example.mayikhstyle.Models;

public class Category {
    private String Category;
    private String Id;
    private  String Url;

    public Category() {
    }
    public Category(String category, String url) {
        Category = category;
        Url = url;
    }

    public Category(String id, String category, String url) {
        Id = id;
        Category = category;
        Url = url;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getUrl() {return Url;}

    public void setUrl(String url) {Url = url;}

    @Override
    public String toString() { return Category; }

}
