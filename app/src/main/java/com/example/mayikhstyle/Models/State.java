package com.example.mayikhstyle.Models;

public class State {
    private String idState;
    private String nameS;

    public State(String idState, String nameS) {
        this.idState = idState;
        this.nameS = nameS;
    }

    public State(String nameS) {
        this.nameS = nameS;
    }

    public String getIdState() {
        return idState;
    }

    public void setIdState(String idState) {
        this.idState = idState;
    }

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }
}
