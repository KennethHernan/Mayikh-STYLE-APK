package com.example.mayikhstyle.Models;

public class Carrito {
    private int idCarrito, idProducto, amount, price;

    private String nameP, description, urlP;
    private String idCarrito2;

    private int idUser;

    public Carrito(int idCarrito, int idProducto, int amount, int price, String nameP, String description, String urlP) {
        this.idCarrito = idCarrito;
        this.idProducto = idProducto;
        this.amount = amount;
        this.price = price;
        this.nameP = nameP;
        this.description = description;
        this.urlP = urlP;
    }

    public Carrito(int idProducto, int amount, int price, String nameP, String description, String urlP, int idUser) {
        this.idProducto = idProducto;
        this.amount = amount;
        this.price = price;
        this.nameP = nameP;
        this.description = description;
        this.urlP = urlP;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
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

    public String getUrlP() {
        return urlP;
    }

    public void setUrlP(String urlP) {
        this.urlP = urlP;
    }

    public int getIdCarrito() {
        return idCarrito;
    }

    public void setIdCarrito(int idCarrito) {
        this.idCarrito = idCarrito;
    }
    public String getIdCarrito2() {
        return idCarrito2;
    }
    public void setIdCarrito2(String idCarrito2) { this.idCarrito2 = idCarrito2; }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
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
}
