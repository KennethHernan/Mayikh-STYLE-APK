package com.example.mayikhstyle.Models;

public class Order {
    private int priceTotal, amountProduct;
    private String idOrder, idUser, idState, fechaCompra;
    private String address;

    public Order(String fechaCompra,int priceTotal, String address, int amountProduct, String idUser, String idState) {
        this.fechaCompra = fechaCompra;
        this.priceTotal = priceTotal;
        this.address = address;
        this.amountProduct = amountProduct;
        this.idUser = idUser;
        this.idState = idState;
    }

    public Order(String idOrder, String fechaCompra, int priceTotal, String address,int amountProduct, String idUser, String idState) {
        this.idOrder = idOrder;
        this.fechaCompra = fechaCompra;
        this.priceTotal = priceTotal;
        this.address = address;
        this.amountProduct = amountProduct;
        this.idUser = idUser;
        this.idState = idState;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
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

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdState() {
        return idState;
    }

    public void setIdState(String idState) {
        this.idState = idState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
