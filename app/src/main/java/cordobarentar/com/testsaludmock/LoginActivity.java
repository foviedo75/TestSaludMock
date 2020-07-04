package cordobarentar.com.testsaludmock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.Api.Cliente;
import cordobarentar.com.testsaludmock.POJO.Empleado;
import cordobarentar.com.testsaludmock.POJO.LoginBody;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private Retrofit mRestAdapter;
    private Api mApi;
    TextView txtUserName;
    TextView txtPassword;
    Button btnLogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtUserName = (TextView) findViewById(R.id.txtUserName);
        txtPassword = (TextView) findViewById(R.id.txtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBarLogin);
        progressBar.setVisibility(View.INVISIBLE);
        //
       /* txtUserName.setText("pruebas@cordobarentar.com");
        txtPassword.setText("Pruebas123$");*/
        txtUserName.setText("fabio@cordobarentar.com");
        txtPassword.setText("Adri345$");



        mApi = AuxCallApi.apiService();



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

    public void onClickLogin(View view){

       if(isOnLine()){

           progressBar.setVisibility(View.VISIBLE);
           btnLogin.setEnabled(false);

           String userName = txtUserName.getText().toString();
           String password = txtPassword.getText().toString();
           Boolean flag = true;
           if (userName.isEmpty() || password.isEmpty())
           {
               flag = false;

           }

           if (flag)
           {
               callLogin(userName, password);
           }
           else
           {
               Toast.makeText(getApplicationContext(),"Usuario o pass en blanco", Toast.LENGTH_SHORT).show();
           }

       }
       else
       {
           Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
       }

    }

    public void  callLogin(String user, String password)
    {
        Call<Empleado> loginCall = mApi.login(new LoginBody(user, password));
        loginCall.enqueue(new Callback<Empleado>(){

            @Override
            public void onResponse(Call<Empleado> call, Response<Empleado> response) {
                if (response.isSuccessful()) {

                    // Guardar afiliado en preferencias
                    SessionPrefs.get(LoginActivity.this).saveAffiliate(response.body());
                    Toast.makeText(getApplicationContext(), "USUARIO LOGUEADO: " + response.body().getempleadoNombre(), Toast.LENGTH_LONG).show();

                    progressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setEnabled(true);

                    //llamar a la siguiente pantalla enl a que listaria los autos
                    startActivity(new Intent(getApplicationContext(), AutosActivity.class));
                    finish();
                }
                else
                {
                    progressBar.setVisibility(View.INVISIBLE);
                    btnLogin.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "User o Pass incorrectos", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<Empleado> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                btnLogin.setEnabled(true);
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();

            }
        });

    }

}