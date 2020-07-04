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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.ActividadesDisplayList;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.POJO.HistoricoBody;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleActividadActivity extends AppCompatActivity {
    ProgressBar progressBarDetailActividad;
    EditText edtPatenteAutoDetalleActividad,edtKilometrajeTableroActividad,edtNotaActividad;
    TextView txtDetalleActividad;
    TextView txtEmpleadoQueHizoActividad;

    FloatingActionButton floatingActionButtonOkActividad;
    private Api mApi;
    private String actividadID;
    private String servicioID;
    private String estadoActividad;
    private String nombreEmpleado;
    private String empleadoID;
    private String kmExcedido;
    private String autoID;
    private String ubicacionID;
    private String empleadoQueHizoLaActividad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_actividad);

        progressBarDetailActividad = (ProgressBar) findViewById(R.id.progressBarDetailActividad);
        progressBarDetailActividad.setVisibility(View.INVISIBLE);
        floatingActionButtonOkActividad = (FloatingActionButton) findViewById(R.id.floatingActionButtonOkActividad);
        edtPatenteAutoDetalleActividad=(EditText) findViewById(R.id.edtPatenteAutoDetalleActividad);

        txtDetalleActividad=(TextView) findViewById(R.id.txtDetalleActividad);
        edtKilometrajeTableroActividad=(EditText) findViewById(R.id.edtKilometrajeTableroActividad);
        edtNotaActividad=(EditText) findViewById(R.id.edtNotaActividad);
        txtEmpleadoQueHizoActividad = (TextView)     findViewById(R.id.txtEmpleadoQueHizoActividad) ;



        //Recuperando los datos que vienen como parametro
        ArrayList<String> datos_actividad = (ArrayList<String>) getIntent().getSerializableExtra("DATOS_ACTIVIDAD");

        actividadID=datos_actividad.get(0);
        edtPatenteAutoDetalleActividad.setText(datos_actividad.get(1));
        txtDetalleActividad.setText(datos_actividad.get(2));
        edtKilometrajeTableroActividad.setText(datos_actividad.get(3));
        kmExcedido=datos_actividad.get(4);
        servicioID=datos_actividad.get(6);
        ubicacionID=datos_actividad.get(7);
        estadoActividad=datos_actividad.get(8);
        edtNotaActividad.setText(datos_actividad.get(9));
        autoID=datos_actividad.get(10);
        empleadoQueHizoLaActividad="Hecho por: "+datos_actividad.get(11);
        txtEmpleadoQueHizoActividad.setText(empleadoQueHizoLaActividad);


        edtKilometrajeTableroActividad.requestFocus();

        nombreEmpleado = SessionPrefs.get(this).getNombreEmpleado();
        empleadoID = SessionPrefs.get(this).getEmpleadoID();
        getSupportActionBar().setTitle("User: "+ nombreEmpleado);

        mApi = AuxCallApi.apiService();


    }

    @Override
    protected void onResume() {
        super.onResume();

        Boolean a = Boolean.valueOf(estadoActividad);
        if(a){

            //floatingActionButtonOkActividad.setEnabled(false);
            floatingActionButtonOkActividad.hide();
            edtKilometrajeTableroActividad.setEnabled(false);
            edtNotaActividad.setEnabled(false);
            txtEmpleadoQueHizoActividad.setVisibility(View.VISIBLE);
        }
        else
        {
            txtEmpleadoQueHizoActividad.setVisibility(View.INVISIBLE);

        }
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

    public void onClickActualizaKMActividad(View view){

        if(isOnLine()){
            String Token = SessionPrefs.get(this).getToken();
            //

            ModificarEstado(Token, edtKilometrajeTableroActividad.getText().toString(), edtNotaActividad.getText().toString());

            /*finish();

            return;*/


        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
        }

    }




    public void ModificarEstado(String Token, String kilometrosActividad, String notaAdcional){


        Calendar calendar = Calendar.getInstance();


       // String stringDate = (calendar.get(Calendar.YEAR) + "-"+ calendar.get(Calendar.MONTH) + "-" + calendar.get(Calendar.DAY_OF_MONTH) );
        int year = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH)+1;
        int dia = calendar.get(Calendar.DAY_OF_MONTH);
        String stringDate =  year   + "-" + mes + "-" + dia;





        Boolean booleanestado = true;

        ActividadesDisplayList updateActividad = new ActividadesDisplayList(actividadID,edtPatenteAutoDetalleActividad.getText().toString(),
                txtDetalleActividad.getText().toString(),nombreEmpleado,edtNotaActividad.getText().toString(),stringDate,
                edtKilometrajeTableroActividad.getText().toString(),booleanestado ,kmExcedido,autoID, servicioID,empleadoID,ubicacionID);

        Call<ActividadesDisplayList> call = mApi.updateActividades(actividadID,"Bearer " + Token, updateActividad);
        call.enqueue(new Callback<ActividadesDisplayList>(){

            @Override
            public void onResponse(Call<ActividadesDisplayList> call, Response<ActividadesDisplayList> response) {
                if (!response.isSuccessful()) {
                    // TODO: Procesar error de API
                    Toast.makeText(getApplicationContext(),"Token Vencido: Logout + Login", Toast.LENGTH_SHORT).show();
                    progressBarDetailActividad.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(),"ERROR 1", Toast.LENGTH_SHORT).show();

                    return;
                    //volver a pantalla de login


                }
                else
                {

                    Toast.makeText(getApplicationContext(),"Update Actividades Exitoso", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ActividadesDisplayList> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"ERROR 2", Toast.LENGTH_SHORT).show();

            }
        });
        //==============================HACER EL HISTORICO ACA
        HistoricoBody datosHistorico = new HistoricoBody(SessionPrefs.get(this).getNombreEmpleado(),
                edtPatenteAutoDetalleActividad.getText().toString(), "Actualizar estado de actividad");

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
                progressBarDetailActividad.setVisibility(View.INVISIBLE);

            }
        });

        //==============================HACER EL HISTORICO ACA



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