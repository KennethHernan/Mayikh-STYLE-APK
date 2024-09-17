package com.example.mayikhstyle.Models;

public class ProductDetails {
    private Product product;
    private Category category;
    private Offers offer;

    public ProductDetails() {}

    // Getters y setters
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public Offers getOffer() { return offer; }
    public void setOffer(Offers offer) { this.offer = offer; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }
}
