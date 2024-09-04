package com.example.aplicacionlicoreria.Models;

public class User {
    private int idUser;
    private int phone;
    private String email;
    private String nameU;
    private String urlU;
    private int idAddress;
    private int idPayment;

    public User() {
    }

    public User(int idUser, int phone, String email, String nameU, String urlU, int idAddress, int idPayment) {
        this.idUser = idUser;
        this.phone = phone;
        this.email = email;
        this.nameU = nameU;
        this.urlU = urlU;
        this.idAddress = idAddress;
        this.idPayment = idPayment;
    }

    public User(int phone, String email, String nameU, String urlU, int idAddress, int idPayment) {
        this.phone = phone;
        this.email = email;
        this.nameU = nameU;
        this.urlU = urlU;
        this.idAddress = idAddress;
        this.idPayment = idPayment;
    }

    public User(int phone, String email, String nameU) {
        this.phone = phone;
        this.email = email;
        this.nameU = nameU;
    }

    public User(int idUser, int phone, String email, String nameU) {
        this.idUser = idUser;
        this.phone = phone;
        this.email = email;
        this.nameU = nameU;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNameU() {
        return nameU;
    }

    public void setNameU(String nameU) {
        this.nameU = nameU;
    }

    public String getUrlU() {
        return urlU;
    }

    public void setUrlU(String urlU) {
        this.urlU = urlU;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }

    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }
}
