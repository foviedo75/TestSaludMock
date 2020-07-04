package cordobarentar.com.testsaludmock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.HistoricoBody;
import cordobarentar.com.testsaludmock.POJO.LavadoBody;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Lavado2Activity extends AppCompatActivity {

    TextView txtPatenteLavado2;
    FloatingActionButton flottingBTNGrabarLavado, flottingBTNModificarKM;
    RadioGroup radioGroup;
    RadioButton radioButton;
    private Retrofit mRestAdapter;
    private Api mApi;
    private Api mHistoricoApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lavado2);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroupLavadores2);

        txtPatenteLavado2 = (TextView) findViewById(R.id.txtPatenteLavado2);

        final ArrayList<String> datos_auto = (ArrayList<String>) getIntent().getSerializableExtra("DATOS_AUTO");
        txtPatenteLavado2.setText(datos_auto.get(1));

        final String autoID = datos_auto.get(0);

        String nombreUsuario = SessionPrefs.get(this).getNombreEmpleado();

        getSupportActionBar().setTitle("User: "+ nombreUsuario);


        flottingBTNGrabarLavado = (FloatingActionButton) findViewById(R.id.flottingBTNGrabarLavado);
        flottingBTNModificarKM = (FloatingActionButton) findViewById(R.id.flottingBTNModificarKM);




        mApi = AuxCallApi.apiService();
        mHistoricoApi= AuxCallApi.apiService();


        flottingBTNGrabarLavado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MakePostLavado(autoID);
            }
        });

        flottingBTNModificarKM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Modificar KM ", Toast.LENGTH_SHORT).show();
                MakeUpdateKm(datos_auto);
            }
        });

    }

    public void MakeUpdateKm(ArrayList<String> localArray){

        if(isOnLine())
        {


            ArrayList<String> milista = new ArrayList<String>();


            milista.addAll(localArray);

           /* //parametros que voy a pasar al activy de Detail Auto
            milista.add(localArray.get(0));;  //0
            milista.add(localArray.get(1));;  //1
            milista.add(localArray.get(2));  //2
            milista.add(localArray.get(3));    //3
            milista.add(localArray.get(4)); //4
            milista.add(localArray.get(5)); //5

            milista.add(localArray.get(6));  //6

            milista.add(localArray.get(7)); //7
            milista.add(localArray.get(8)); //8
            milista.add(localArray.get(9)); //9

            milista.add(localArray.get(10)); //10
            milista.add(localArray.get(11)); //11
            milista.add(localArray.get(12)); //12*/

            Intent intent = new Intent(getApplicationContext(), AutoDetailActivity.class);
            intent.putExtra("DATOS_AUTO", milista);
            startActivity(intent);    //aca llama a la pantalla donde modifico los kilometros






        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
        }

    }

    public void MakePostLavado(String autoID)
    {
        if(isOnLine())
        {

            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButton = findViewById(radioId);

            //Toast.makeText(getApplicationContext(),"ID Vehiculo "+ autoID, Toast.LENGTH_SHORT).show();

            final String nombreLavador = radioButton.getText().toString();
            //Toast.makeText(getApplicationContext(), nombreLavador,Toast.LENGTH_SHORT).show();
            boolean LavadoPagadoYN = false;
            String AutoPatente = txtPatenteLavado2.getText().toString();
            double LavadoPrecio = 100;
            String AutoID = autoID;
            String EmpleadoID=SessionPrefs.get(this).getEmpleadoID();
            String Token = SessionPrefs.get(this).getToken();
            //hacer la peticion POST http://cordobarentar.com/api/LavadosApi

            LavadoBody datosLavado = new LavadoBody(nombreLavador, LavadoPagadoYN, AutoPatente,
                    LavadoPrecio,AutoID ,EmpleadoID) ;

            Call<LavadoBody> call = mApi.insertLavado("Bearer " + Token, datosLavado);
            call.enqueue(new Callback<LavadoBody>(){


                @Override
                public void onResponse(Call<LavadoBody> call, Response<LavadoBody> response) {
                    if (response.isSuccessful())
                    {
                        //Toast.makeText(getApplicationContext(), "Lavado grabado okok", Toast.LENGTH_LONG).show();

                        //finish();
                        //return;
                    }
                    else
                    {

                        Toast.makeText(getApplicationContext(), "Error de seguridad al grabar el lavado", Toast.LENGTH_LONG).show();

                    }
                }

                @Override
                public void onFailure(Call<LavadoBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

                }
            });

            //======================================HISTORICO==========================
            HistoricoBody datosHistorico = new HistoricoBody(SessionPrefs.get(this).getNombreEmpleado(), AutoPatente, "Lavado de auto");

           /* Call<LavadoBody> call = mApi.insertLavado("Bearer " + Token,datosLavado);
            call.enqueue(new Callback<LavadoBody>()*/

            //miapi.insertHistorico("Bearer "+ txtTOKEN.getText().toString(), datosHistorico).enqueue(new Callback<HistoricoBody>()
            Call<HistoricoBody> call2;  //le tuve que hacer un split porque si no me daba error porque ya existe call
            call2 = mApi.insertHistorico("Bearer " + Token,datosHistorico);
            call2.enqueue(new Callback<HistoricoBody>() {

                @Override
                public void onResponse(Call<HistoricoBody> call, Response<HistoricoBody> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<HistoricoBody> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            //======================================HISTORICO==========================



            }
        else
            {
                Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
            }

    }









    public boolean isOnLine(){
        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnectedOrConnecting())
        {return true;}
        else
        {return false;}

    }


}