package com.example.aplicacionlicoreria.Models;


public class Payment {
    private int idPayment,idUser, month, year,cvv;
    private String cardNumber;

    public Payment() {
    }

    public Payment(int idPayment, int idUser, String cardNumber, int month, int year, int cvv) {
        this.idPayment = idPayment;
        this.idUser = idUser;
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public Payment(int idUser, String cardNumber, int month, int year, int cvv) {
        this.idUser = idUser;
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public Payment(String cardNumber, int month, int year, int cvv) {
        this.cardNumber = cardNumber;
        this.month = month;
        this.year = year;
        this.cvv = cvv;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdPayment() {
        return idPayment;
    }

    public void setIdPayment(int idPayment) {
        this.idPayment = idPayment;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }
}
