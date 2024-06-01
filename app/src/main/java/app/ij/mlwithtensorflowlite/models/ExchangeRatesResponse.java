package app.ij.mlwithtensorflowlite.models;
import com.google.gson.Gson;

import java.util.List;

public class ExchangeRatesResponse {
    private Currency usd;
    private Currency euro;

    // Getters and Setters
    public Currency getUsd() {
        return usd;
    }

    public void setUsd(Currency usd) {
        this.usd = usd;
    }

    public Currency getEuro() {
        return euro;
    }

    public void setEuro(Currency euro) {
        this.euro = euro;
    }
}



