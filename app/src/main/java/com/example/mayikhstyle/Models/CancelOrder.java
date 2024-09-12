package com.example.mayikhstyle.Models;

public class CancelOrder {
    private int idCancelOrder;
    private String idCancelOrder2;
    private  String descriptionCancel;
    private int idOrder;

    private int PrecioTotal;
    private int AmountProduct;
    private int idUser;
    private String NameState;

    public CancelOrder(String descriptionCancel, int idOrder) {
        this.descriptionCancel = descriptionCancel;
        this.idOrder = idOrder;
    }

    public CancelOrder(int idCancelOrder, String descriptionCancel, int idOrder, int precioTotal, int amountProduct, int idUser, String nameState) {
        this.idCancelOrder = idCancelOrder;
        this.descriptionCancel = descriptionCancel;
        this.idOrder = idOrder;
        PrecioTotal = precioTotal;
        AmountProduct = amountProduct;
        this.idUser = idUser;
        NameState = nameState;
    }

    public CancelOrder(String descriptionCancel, int idOrder, int precioTotal, int amountProduct, int idUser, String nameState) {
        this.descriptionCancel = descriptionCancel;
        this.idOrder = idOrder;
        PrecioTotal = precioTotal;
        AmountProduct = amountProduct;
        this.idUser = idUser;
        NameState = nameState;
    }

    public int getPrecioTotal() {
        return PrecioTotal;
    }

    public void setPrecioTotal(int precioTotal) {
        PrecioTotal = precioTotal;
    }

    public int getAmountProduct() {
        return AmountProduct;
    }

    public void setAmountProduct(int amountProduct) {
        AmountProduct = amountProduct;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getNameState() {
        return NameState;
    }

    public void setNameState(String nameState) {
        NameState = nameState;
    }

    public int getIdCancelOrder() {
        return idCancelOrder;
    }

    public void setIdCancelOrder(int idCancelOrder) {
        this.idCancelOrder = idCancelOrder;
    }
    public String getIdCancelOrder2() {
        return idCancelOrder2;
    }

    public void setIdCancelOrder2(String idCancelOrder2) {
        this.idCancelOrder2 = idCancelOrder2;
    }

    public String getDescriptionCancel() {
        return descriptionCancel;
    }

    public void setDescriptionCancel(String descriptionCancel) {
        this.descriptionCancel = descriptionCancel;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }
}
