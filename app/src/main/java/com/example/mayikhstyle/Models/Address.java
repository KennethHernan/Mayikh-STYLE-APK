package com.example.mayikhstyle.Models;

public class Address {
    private int idAddress;
    private String idAddress2;
    private String nameA;
    private String adrress;
    private int idUser;

    public Address() {
    }

    public Address(int idAddress, String nameA, String adrress, int idUser) {
        this.idAddress = idAddress;
        this.nameA = nameA;
        this.adrress = adrress;
        this.idUser = idUser;
    }

    public Address(int idAddress, String nameA, String adrress) {
        this.idAddress = idAddress;
        this.nameA = nameA;
        this.adrress = adrress;
    }

    public Address(String nameA, String adrress) {
        this.nameA = nameA;
        this.adrress = adrress;
    }

    public Address(String nameA, String adrress, int idUser) {
        this.nameA = nameA;
        this.adrress = adrress;
        this.idUser = idUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdAddress() {
        return idAddress;
    }

    public void setIdAddress(int idAddress) {
        this.idAddress = idAddress;
    }
    public String getIdAddress2() {
        return idAddress2;
    }

    public void setIdAddress2(String idAddress2) {
        this.idAddress2 = idAddress2;
    }
    public String getNameA() {
        return nameA;
    }

    public void setNameA(String nameA) {
        this.nameA = nameA;
    }

    public String getAdrress() {
        return adrress;
    }

    public void setAdrress(String adrress) {
        this.adrress = adrress;
    }
}
