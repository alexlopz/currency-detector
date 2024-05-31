package app.ij.mlwithtensorflowlite.utils;

public class CurrencyConstants {
    public static final String UN_QUETZAL = "1 Quetzal";
    public static final String CINCO_QUETZALES = "5 Quetzales";
    public static final String DIEZ_QUETZALES = "10 Quetzales";
    public static final String VEINTE_QUETZALES = "20 Quetzales";
    public static final String CINCUENTA_QUETZALES = "50 Quetzales";
    public static final String CIEN_QUETZALES = "100 Quetzales";

    public static final String[] MONEDAS_CONSTANTES = {
            UN_QUETZAL,
            CINCO_QUETZALES,
            DIEZ_QUETZALES,
            VEINTE_QUETZALES,
            CINCUENTA_QUETZALES,
            CIEN_QUETZALES
    };

    private CurrencyConstants() {
        throw new UnsupportedOperationException("Esta es una clase de constantes y no puede ser instanciada.");
    }
}
