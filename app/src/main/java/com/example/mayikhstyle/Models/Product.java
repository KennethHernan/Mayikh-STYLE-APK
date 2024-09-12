package com.example.mayikhstyle.Models;

public class Product {
    private String nameP;
    private String description;
    private int price;
    private String idProduct, idCategory, idOffers;
    private String urlP;
    private int stock;
    private String descriptionOffers;
    private String nameC;
    private String urlC;

    public Product() {
    }
    public Product(String idProduct, String nameP, String description, int price, String urlP, int stock, String idCategory, String idOffers) {
        this.idProduct = idProduct;
        this.nameP = nameP;
        this.description = description;
        this.price = price;
        this.urlP = urlP;
        this.stock = stock;
        this.idCategory = idCategory;
        this.idOffers = idOffers;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(String idCategory) {
        this.idCategory = idCategory;
    }

    public String getIdOffers() {
        return idOffers;
    }

    public void setIdOffers(String idOffers) {
        this.idOffers = idOffers;
    }

    public String getUrlP() {
        return urlP;
    }

    public void setUrlP(String urlP) {
        this.urlP = urlP;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getDescriptionOffers() {
        return descriptionOffers;
    }

    public void setDescriptionOffers(String descriptionOffers) {
        this.descriptionOffers = descriptionOffers;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
    }

    public String getUrlC() {
        return urlC;
    }

    public void setUrlC(String urlC) {
        this.urlC = urlC;
    }
}
