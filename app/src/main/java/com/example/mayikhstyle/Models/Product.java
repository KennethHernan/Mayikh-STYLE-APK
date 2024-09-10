package com.example.mayikhstyle.Models;

public class Product {
    private int idProduct;
    private String nameP;
    private String description;
    private int price;
    private int idCategory;
    private String urlP;
    private int stock;
    private int idOffers;
    private int discount;
    private String descriptionOffers;
    private String nameC;
    private String urlC;

    public Product() {
    }

    public Product(int idProduct) {
        this.idProduct = idProduct;
    }

    public Product(int idProduct, int idOffers) {
        this.idProduct = idProduct;
        this.idOffers = idOffers;
    }

    public Product(int idProduct, String nameP, String description, int price, int idCategory, String urlP, int stock, int idOffers, int discount) {
        this.idProduct = idProduct;
        this.nameP = nameP;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.urlP = urlP;
        this.stock = stock;
        this.idOffers = idOffers;
        this.discount = discount;
    }
    public Product(int idProduct, String nameP, String description, int price, int idCategory, String urlP, int stock, int idOffers, int discount,String descriptionOffers) {
        this.idProduct = idProduct;
        this.nameP = nameP;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.urlP = urlP;
        this.stock = stock;
        this.idOffers = idOffers;
        this.discount = discount;
        this.descriptionOffers = descriptionOffers;
    }

    public Product(String nameP, String description, int price, int idCategory, String urlP, int stock, int idOffers) {
        this.nameP = nameP;
        this.description = description;
        this.price = price;
        this.idCategory = idCategory;
        this.urlP = urlP;
        this.stock = stock;
        this.idOffers = idOffers;
    }


    public Product(String nameC, String urlC, int idProduct,String nameP, String description, int price, String urlP) {
        this.nameC = nameC;
        this.urlC = urlC;
        this.idProduct = idProduct;
        this.nameP = nameP;
        this.description = description;
        this.price = price;
        this.urlP = urlP;
    }

    public String getDescriptionOffers() {
        return descriptionOffers;
    }

    public void setDescriptionOffers(String descriptionOffers) {
        this.descriptionOffers = descriptionOffers;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getIdOffers() {
        return idOffers;
    }

    public void setIdOffers(int idOffers) {
        this.idOffers = idOffers;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getNameC() {
        return nameC;
    }

    public void setNameC(String nameC) {
        this.nameC = nameC;
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

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getUrlP() {
        return urlP;
    }

    public void setUrlP(String urlP) {
        this.urlP = urlP;
    }

    public String getUrlC() {
        return urlC;
    }

    public void setUrlC(String urlC) {
        this.urlC = urlC;
    }
}
