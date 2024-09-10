package com.example.mayikhstyle.Models;

public class State {
    private int idState;
    private String nameS;

    public State(int idState, String nameS) {
        this.idState = idState;
        this.nameS = nameS;
    }

    public State(String nameS) {
        this.nameS = nameS;
    }

    public int getIdState() {
        return idState;
    }

    public void setIdState(int idState) {
        this.idState = idState;
    }

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }
}
