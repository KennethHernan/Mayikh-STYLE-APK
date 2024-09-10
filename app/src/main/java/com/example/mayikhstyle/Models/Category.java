package com.example.mayikhstyle.Models;

public class Category {
    private int Id;
    private String Category;
    private  String Url;

    public Category() {
    }
    public Category(String category, String url) {
        Category = category;
        Url = url;
    }
    public Category(int id, String category, String url) {
        Id = id;
        Category = category;
        Url = url;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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
}
