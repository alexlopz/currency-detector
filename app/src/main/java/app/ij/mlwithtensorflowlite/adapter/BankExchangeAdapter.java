package app.ij.mlwithtensorflowlite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import app.ij.mlwithtensorflowlite.R;
import app.ij.mlwithtensorflowlite.models.Bank;

public class BankExchangeAdapter extends RecyclerView.Adapter<BankExchangeAdapter.ViewHolder> {
    public List<Bank> banks;
    final private Context context;
    private final double currencyQuantity;
    private String currency;
    private String denomination;

    public BankExchangeAdapter(final List<Bank> banks,
                               final Context context,
                               final double currencyQuantity,
                               final String currency,
                                final String denomination) {
        this.banks = banks;
        this.context = context;
        this.currencyQuantity = currencyQuantity;
        this.currency = currency;
        this.denomination = denomination;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_exchange,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        final Bank bank = banks.get(index);
        final String banckName = getBankName(bank.getNombre());
        final int bankLogo = getBankLogo(bank.getNombre());

        final double exchangeTotal = getTotal(currencyQuantity, bank.getVenta());


        viewHolder.bankNameTxt.setText(banckName);
        viewHolder.bankCompTxt.setText("Compra: " + currency + bank.getCompra());
        viewHolder.bankVentTxt.setText("Venta:" + currency + bank.getVenta());
        viewHolder.bankLogoImg.setImageResource(bankLogo);
        viewHolder.bankTotalTxt.setText(String.valueOf(currency + exchangeTotal));


    }

    private double getTotal(double quantity, String venta){
        if (denomination.equals("Quetzal") || denomination.equals("Quetzales") || denomination.equals("Centavos")) {
            return getExchangeDividerTotal(quantity, venta);
        } else {
            return getExchangeTotal(quantity, venta);
        }
    }



    @Override
    public int getItemCount() {
        return banks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView bankNameTxt, bankCompTxt, bankVentTxt, bankTotalTxt;
        ImageView bankLogoImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            bankNameTxt = itemView.findViewById(R.id.bank_name_txt);
            bankCompTxt = itemView.findViewById(R.id.bank_comp_txt);
            bankVentTxt = itemView.findViewById(R.id.bank_vent_txt);
            bankTotalTxt = itemView.findViewById(R.id.bank_total_txt);
            bankLogoImg = itemView.findViewById(R.id.bank_logo_img);
        }
    }

    public static double getExchangeTotal(double quantity, String venta) {
        // Verificar que la cantidad no sea negativa
        if (quantity < 0) {
            return 0.0;
        }

        try {
            // Convertir la cadena venta a un double
            double cambioDouble = Double.parseDouble(venta);

            // Verificar que el venta no sea negativo
            if (cambioDouble < 0) {
                return 0.0;
            }

            // Multiplicar el valor convertido por quantity
            double resultado = quantity * cambioDouble;

            // Formatear el resultado a 2 decimales
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.parseDouble(df.format(resultado));
        } catch (NumberFormatException e) {
            // Manejo de excepción en caso de que la cadena no pueda ser convertida a un número
            return 0.0;
        }
    }

    public static double getExchangeDividerTotal(double quantity, String venta) {
        // Verificar que la cantidad no sea negativa
        if (quantity < 0) {
            System.out.println("Error: La cantidad no puede ser negativa.");
            return 0.0;
        }

        try {
            // Convertir la cadena venta a un double
            double cambioDouble = Double.parseDouble(venta);

            // Verificar que el valor de venta no sea negativo o cero
            if (cambioDouble <= 0) {
                System.out.println("Error: El valor de venta no puede ser negativo o cero.");
                return 0.0;
            }

            // Dividir quantity por el valor convertido
            double resultado = quantity / cambioDouble;

            // Formatear el resultado a 2 decimales
            DecimalFormat df = new DecimalFormat("#.00");
            return Double.parseDouble(df.format(resultado));
        } catch (NumberFormatException e) {
            // Manejo de excepción en caso de que la cadena no pueda ser convertida a un número
            System.out.println("Error: La cadena de venta no es un número válido.");
            return 0.0;
        } catch (ArithmeticException e) {
            // Manejo de excepción en caso de errores aritméticos, como división por cero
            System.out.println("Error: Ocurrió un error aritmético.");
            return 0.0;
        }
    }

    private String getBankName(final String name) {
        switch (name) {
            case "BI":
                return "Banco Industrial";
            case "BAM":
                return "Banco Agromercantil";
            case "Bantrab":
                return "Banco de los Trabajadores";
            case "BancoPromerica":
                return "Banco Promerica";
            case "CHN":
                return "Banco Agromercantil";
            case "BancoInmobiliario":
                return "Banco Inmobiliario";
            case "Ficohsa":
                return "Banco Ficohsa";
            case "Banrural":
                return "Banco Banrural";
            case "Gyt":
                return "Banco Gyt Continental";
            default:
                return name;
        }
    }

    private int getBankLogo(final String name) {
        switch (name) {
            case "BI":
                return R.drawable.bi_logo_white;
            case "BAM":
                return R.drawable.bam_logo_white;
            case "Bantrab":
                return R.drawable.bantrab_logo_white;
            case "BancoPromerica":
                return R.drawable.promerica_logo_white;
            case "CHN":
                return R.drawable.chn_logo_white;
            case "BancoInmobiliario":
                return R.drawable.inmobiliario_logo_white;
            case "Ficohsa":
                return R.drawable.ficohsa_logo_white;
            case "Banrural":
                return R.drawable.banrural_logo_white;
            case "Gyt":
                return R.drawable.gyt_logo_white;
            default:
                return R.drawable.bi_logo_white;
        }
    }

}
