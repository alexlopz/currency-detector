package app.ij.mlwithtensorflowlite.remote;

public class ApiUtil {
    public ApiUtil() {
    }

    public static final String BASE_URL = "http://85.239.243.114/IA/api/";

    public static ApiService getAPIService(){
        return RetrofitClient.getClient(BASE_URL).create(ApiService.class);
    }

}
