package cordobarentar.com.testsaludmock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.POJO.HistoricoBody;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutoDetailActivity extends AppCompatActivity {

    EditText edtPatenteAuto, edtModeloAuto, edtColorAuto, edtNuevoKilometraje,edtKilometrajeReal,
            edtKilometrajeBajado,edtCodigoRadio,edtLocalizador,edtLucesAutomaticas;
    FloatingActionButton flottingBTNGrabarKm;

    ProgressBar progressBarDetailAuto;
    private String autoID;
    private String ubicacionID;
    private Boolean autoActivoYN;
    private Boolean ActividadesPendientesYN;
    private Api mApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_detail);

        String nombreUsuario = SessionPrefs.get(this).getNombreEmpleado();

        getSupportActionBar().setTitle("User: "+ nombreUsuario);

        flottingBTNGrabarKm = (FloatingActionButton) findViewById(R.id.flottingBTNGrabarActividad);

        edtPatenteAuto = (EditText) findViewById(R.id.edtPatenteAutoDetalleActividad);
        edtModeloAuto = (EditText) findViewById(R.id.edtModeloAutoDetalleActividad);
        edtColorAuto = (EditText) findViewById(R.id.edtColorAutoDetalleActividad);
        edtNuevoKilometraje = (EditText) findViewById(R.id.edtNuevoKilometraje);
        edtKilometrajeReal = (EditText) findViewById(R.id.edtKilometrajeTableroActividad);
        edtKilometrajeBajado = (EditText) findViewById(R.id.edtKilometrajeBajado);
        edtCodigoRadio = (EditText) findViewById(R.id.edtCodigoRadio);
        edtLocalizador = (EditText) findViewById(R.id.edtLocalizador);
        edtLucesAutomaticas = (EditText) findViewById(R.id.edtLucesAutomaticas);

        //Recuperando los datos que vienen como parametro
        ArrayList<String> datos_auto = (ArrayList<String>) getIntent().getSerializableExtra("DATOS_AUTO");
        //final String autoID = datos_auto.get(0);
        autoID= datos_auto.get(0);
        edtPatenteAuto.setText(datos_auto.get(1));
        edtModeloAuto.setText(datos_auto.get(2));
        edtColorAuto.setText(datos_auto.get(3));
        edtNuevoKilometraje.setText(datos_auto.get(4));
       edtKilometrajeBajado.setText(datos_auto.get(5));
        edtKilometrajeReal.setText(datos_auto.get(6));
        edtCodigoRadio.setText(datos_auto.get(7));
       edtLocalizador.setText(datos_auto.get(8));
       edtLucesAutomaticas.setText(datos_auto.get(9));
       ubicacionID =datos_auto.get(10);
        autoActivoYN = Boolean.valueOf(datos_auto.get(11));
        ActividadesPendientesYN= Boolean.valueOf(datos_auto.get(12));

        progressBarDetailAuto = (ProgressBar) findViewById(R.id.progressBarDetailAuto);


        edtNuevoKilometraje.requestFocus();
        progressBarDetailAuto.setVisibility(View.INVISIBLE);

        mApi = AuxCallApi.apiService();


    }

    public void onClickActualizaKM(View view){

        if(isOnLine()){

            progressBarDetailAuto.setVisibility(View.VISIBLE);



            hacerUpdate(edtNuevoKilometraje.getText().toString());

            finish();

            return;

        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
        }


    }

    public void hacerUpdate(String nuevoKilometraje){

        String localAutoID = autoID;
        String nuevoKm = nuevoKilometraje;


        Integer kilometrosTablero = Integer.valueOf(nuevoKm);
        Integer KilometrosBajados = Integer.valueOf(edtKilometrajeBajado.getText().toString());
        Integer kilometrosReales = kilometrosTablero + KilometrosBajados;
        String nuevoKilometrajeReal = kilometrosReales.toString();
        String Token = SessionPrefs.get(this).getToken();



        AutosDisplayList updateAuto = new AutosDisplayList(autoID, edtPatenteAuto.getText().toString(),
                edtModeloAuto.getText().toString(),edtColorAuto.getText().toString(),edtCodigoRadio.getText().toString(),
                edtLocalizador.getText().toString(), nuevoKilometraje,
                edtKilometrajeBajado.getText().toString(),nuevoKilometrajeReal,autoActivoYN,
                ubicacionID,ActividadesPendientesYN ,Boolean.valueOf(edtLucesAutomaticas.getText().toString()) );


        Call<AutosDisplayList> call = mApi.updateAuto(autoID,"Bearer " + Token, updateAuto);
        call.enqueue(new Callback<AutosDisplayList>(){


            @Override
            public void onResponse(Call<AutosDisplayList> call, Response<AutosDisplayList> response) {
                Toast.makeText(getApplicationContext(),"UPDATE OK OK", Toast.LENGTH_SHORT).show();
                //startActivity(new Intent(getApplicationContext(), AutosActivity.class));
               /* finish();
                return;*/

            }

            @Override
            public void onFailure(Call<AutosDisplayList> call, Throwable t) {

                progressBarDetailAuto.setVisibility(View.INVISIBLE);

                Toast.makeText(getApplicationContext(),"Error al hacer update de la info de Autos", Toast.LENGTH_SHORT).show();

            }
        });
        //==============================HACER EL HISTORICO ACA
        HistoricoBody datosHistorico = new HistoricoBody(SessionPrefs.get(this).getNombreEmpleado(),
                edtPatenteAuto.getText().toString(), "Modificar Kilometros");

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
                progressBarDetailAuto.setVisibility(View.INVISIBLE);

            }
        });


        //==============================HACER EL HISTORICO ACA






    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.auto_detail_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_volver:
                Volver();
                return true;
            /*case R.id.action_logOut:
                openLogout();
                return true;*/
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void Volver()
    {
        /*SessionPrefs.get(this).logOut();
        startActivity(new Intent(this, LoginActivity.class));*/
        finish();
        return;


    }







    //verificando la conexion
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