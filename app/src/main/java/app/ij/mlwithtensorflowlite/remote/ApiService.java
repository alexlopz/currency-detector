package app.ij.mlwithtensorflowlite.remote;

import app.ij.mlwithtensorflowlite.models.ExchangeRatesResponse;
import retrofit2.http.GET;

public interface ApiService {
    @GET("Values/")
    rx.Observable<ExchangeRatesResponse> getExchangeGetRateResponse(
    );
}
