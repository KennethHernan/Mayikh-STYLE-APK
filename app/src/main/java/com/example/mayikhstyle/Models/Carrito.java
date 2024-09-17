package com.example.mayikhstyle.Models;

public class Carrito {
    private int amount, price;

    private String idCarrito, idProduct, nameP, descriptionCar, urlP, idUser;

    public Carrito(){

    }
    public Carrito(String idCarrito, String idProduct, int amount, int price, String nameP, String descriptionCar, String urlP, String idUser) {
        this.idCarrito = idCarrito;
        this.idProduct = idProduct;
        this.amount = amount;
        this.price = price;
        this.nameP = nameP;
        this.descriptionCar = descriptionCar;
        this.urlP = urlP;
        this.idUser = idUser;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(String idCarrito) {
        this.idCarrito = idCarrito;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getNameP() {
        return nameP;
    }

    public void setNameP(String nameP) {
        this.nameP = nameP;
    }

    public String getDescriptionCar() {
        return descriptionCar;
    }

    public void setDescriptionCar(String descriptionCar) {
        this.descriptionCar = descriptionCar;
    }

    public String getUrlP() {
        return urlP;
    }

    public void setUrlP(String urlP) {
        this.urlP = urlP;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
