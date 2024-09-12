package com.example.mayikhstyle.Models;

public class Offers {
    private String idOffers;
    private int discount;
    private String descriptionO;

    public Offers() {
    }

    public Offers(String idOffers, int discount, String descriptionO) {
        this.idOffers = idOffers;
        this.discount = discount;
        this.descriptionO = descriptionO;
    }

    public Offers(int discount, String descriptionO) {
        this.discount = discount;
        this.descriptionO = descriptionO;
    }

    public String getIdOffers() {
        return idOffers;
    }

    public void setIdOffers(String idOffers) {
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

    @Override
    public String toString() {
        return String.valueOf(discount);
    }
}
