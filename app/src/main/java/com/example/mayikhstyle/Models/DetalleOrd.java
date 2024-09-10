package com.example.mayikhstyle.Models;

public class DetalleOrd {
    private int idDetalleOrd, idProduct, price, amount, idOrder;
    private  String nameP,descriptionP,urlP;
    public DetalleOrd(int idDetalleOrd) {
        this.idDetalleOrd = idDetalleOrd;
    }

    public DetalleOrd(int idDetalleOrd, int idProduct, int price, int amount, int idOrder) {
        this.idDetalleOrd = idDetalleOrd;
        this.idProduct = idProduct;
        this.price = price;
        this.amount = amount;
        this.idOrder = idOrder;
    }

    public DetalleOrd(int idDetalleOrd, int idProduct, int price, int amount, String nameP, String descriptionP, String urlP) {
        this.idDetalleOrd = idDetalleOrd;
        this.idProduct = idProduct;
        this.price = price;
        this.amount = amount;
        this.nameP = nameP;
        this.descriptionP = descriptionP;
        this.urlP = urlP;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getDescriptionP() {
        return descriptionP;
    }

    public void setDescriptionP(String descriptionP) {
        this.descriptionP = descriptionP;
    }

    public String getUrlP() {
        return urlP;
    }

    public void setUrlP(String urlP) {
        this.urlP = urlP;
    }

    public int getIdDetalleOrd() {
        return idDetalleOrd;
    }

    public void setIdDetalleOrd(int idDetalleOrd) {
        this.idDetalleOrd = idDetalleOrd;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
