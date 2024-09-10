package com.example.mayikhstyle.Models;

public class Offers {
    private int idOffers;
    private int discount;
    private String descriptionO;

    public Offers() {
    }

    public Offers(int idOffers, int discount, String descriptionO) {
        this.idOffers = idOffers;
        this.discount = discount;
        this.descriptionO = descriptionO;
    }

    public Offers(int discount, String descriptionO) {
        this.discount = discount;
        this.descriptionO = descriptionO;
    }

    public int getIdOffers() {
        return idOffers;
    }

    public void setIdOffers(int idOffers) {
        this.idOffers = idOffers;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getDescriptionO() {
        return descriptionO;
    }

    public void setDescriptionO(String descriptionO) {
        this.descriptionO = descriptionO;
    }
}
