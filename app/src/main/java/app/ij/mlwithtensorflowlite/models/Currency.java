package app.ij.mlwithtensorflowlite.models;

import java.util.List;

public class Currency {
    private List<Bank> bancos;

    // Getters and Setters
    public List<Bank> getBancos() {
        return bancos;
    }

    public void setBancos(List<Bank> bancos) {
        this.bancos = bancos;
    }
}