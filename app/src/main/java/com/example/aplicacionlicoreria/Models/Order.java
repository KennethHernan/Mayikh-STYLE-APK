package com.example.aplicacionlicoreria.Models;

public class Order {
    private int idOrder, priceTotal, amountProduct;
    private String fechaCompra;
    private int idUser;
    private String address;
    private int idState;

    public Order(String fechaCompra,int priceTotal, String address, int amountProduct, int idUser, int idState) {
        this.fechaCompra = fechaCompra;
        this.priceTotal = priceTotal;
        this.address = address;
        this.amountProduct = amountProduct;
        this.idUser = idUser;
        this.idState = idState;
    }

    public Order(int idOrder, String fechaCompra, int priceTotal, String address,int amountProduct, int idUser, int idState) {
        this.idOrder = idOrder;
        this.fechaCompra = fechaCompra;
        this.priceTotal = priceTotal;
        this.address = address;
        this.amountProduct = amountProduct;
        this.idUser = idUser;
        this.idState = idState;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getPriceTotal() {
        return priceTotal;
    }

    public void setPriceTotal(int priceTotal) {
        this.priceTotal = priceTotal;
    }
    public int getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(int amountProduct) {
        this.amountProduct = amountProduct;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }
}
