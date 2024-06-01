

package app.ij.mlwithtensorflowlite;

import static app.ij.mlwithtensorflowlite.utils.CurrencyConstantsAll.MONEDAS_CONSTANTES;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import app.ij.mlwithtensorflowlite.adapter.BankExchangeAdapter;
import app.ij.mlwithtensorflowlite.ml.Model;
import app.ij.mlwithtensorflowlite.models.ExchangeRatesResponse;
import app.ij.mlwithtensorflowlite.remote.ApiService;
import app.ij.mlwithtensorflowlite.remote.ApiUtil;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView resultQuantity, resultCurrency;
    private ImageView imageView;
    private FloatingActionButton picture;
    private int imageSize = 224;
    private ApiService apiService;
    private ProgressDialog dlg;
    private ExchangeRatesResponse exchangeRatesResponse;
    private BankExchangeAdapter bankExchangeAdapter;
    private RecyclerView bankExchangeRecycler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultQuantity = findViewById(R.id.resultQuantity);
        resultCurrency = findViewById(R.id.resultCurrency);
        imageView = findViewById(R.id.imageView);
        picture = findViewById(R.id.button);
        apiService = ApiUtil.getAPIService();
        bankExchangeRecycler = findViewById(R.id.banks_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        bankExchangeRecycler.setLayoutManager(layoutManager);

        picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 1);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getApplicationContext());

            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int [] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());

            int pixel = 0;
            for(int i = 0; i < imageSize; i++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255.f));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255.f));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            int maxPos = 0;
            float maxConfidence = 0;
            for(int i = 0; i < confidences.length; i++){
                if(confidences[i] > maxConfidence){
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }

            final String[] result = MONEDAS_CONSTANTES[maxPos].split(" ");

            resultQuantity.setText(result[0]);
            resultCurrency.setText(result[1]);


            model.close();
            getDataBank(parseToDouble(result[0]), result[1]);
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int dimension = Math.min(image.getWidth(), image.getHeight());
            image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
            imageView.setImageBitmap(image);

            image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
            classifyImage(image);

            showProgress();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void getDataBank(double currencyQuantity, String denomination) {
        apiService.getExchangeGetRateResponse()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ExchangeRatesResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        dlg.dismiss();
                    }

                    @Override
                    public void onNext(ExchangeRatesResponse response) {
                        exchangeRatesResponse = response;
                        Log.d("exchangeRatesResponse", exchangeRatesResponse.toString());
                        dlg.dismiss();

                        bankExchangeAdapter = new BankExchangeAdapter(response.getUsd().getBancos(), MainActivity.this, currencyQuantity, getCurrencyConversion(denomination), denomination);
                        bankExchangeRecycler.setAdapter(bankExchangeAdapter);
                        bankExchangeAdapter.notifyDataSetChanged();
                    }
                });
    }

    private String getCurrencyConversion(String denomination){
        switch (denomination) {
            case "Quetzal":
            case "Quetzales":
            case "Centavos":
                return "$ ";
            case "Dolar":
            case "Dolares":
                return "Q ";
            case "Euros":
                return "€ ";
            default:
                return "NA ";
        }
    }

    public void showProgress() {
        dlg = new ProgressDialog(this);
        dlg.setMessage("Cargando...");
        dlg.setCancelable(false);
        dlg.show();
    }

    public double parseToDouble(String value) {
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

}