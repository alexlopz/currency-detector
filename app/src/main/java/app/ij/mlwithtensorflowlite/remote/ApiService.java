package app.ij.mlwithtensorflowlite.remote;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService {
    @GET("Values/")//OBTENER PERFIL DE EMPRESA

    rx.Observable<Object> registerUser(
            @Body Object user
    );
}
