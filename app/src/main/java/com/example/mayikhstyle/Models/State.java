package com.example.mayikhstyle.Models;

public class State {
    private int idState;
    private String nameS, idState2;

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

    public String getIdState2() {
        return idState2;
    }

    public void setIdState2(String idState2) {
        this.idState2 = idState2;
    }

    public String getNameS() {
        return nameS;
    }

    public void setNameS(String nameS) {
        this.nameS = nameS;
    }
}
