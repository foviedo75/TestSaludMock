package cordobarentar.com.testsaludmock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cordobarentar.com.testsaludmock.Adapters.ActividadesAdapter;
import cordobarentar.com.testsaludmock.Adapters.AutosAdapter;
import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.ActividadesDisplayList;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.R;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActividadesActivity extends AppCompatActivity {

    private Api mApi;
    private RecyclerView recyclerViewActividades;
    private ActividadesAdapter mActividadesAdapter;
    ProgressBar progressBarActividades;

    SwipeRefreshLayout swipeRefreshLayoutActividades;
    private String modoLlamado;
    private String autoIdOrigenLlamado;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividades);



        recyclerViewActividades =(RecyclerView) findViewById(R.id.recyclerViewListaActividades);
        recyclerViewActividades.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerActividades = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerActividades.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewActividades.setLayoutManager(linearLayoutManagerActividades);
        progressBarActividades = (ProgressBar)findViewById(R.id.progressBarActividades);
        progressBarActividades.setVisibility(View.VISIBLE);



        mApi = AuxCallApi.apiService();
        swipeRefreshLayoutActividades = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshActividades);
        swipeRefreshLayoutActividades.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                String Token = SessionPrefs.get(getApplicationContext()).getToken();
                loadActividades(Token);
                mActividadesAdapter.actualizarItems(null);
                swipeRefreshLayoutActividades.setRefreshing(false);

            }
        });







    }

    //para el search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actividades_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_searchActividades:
                openSearch(item);
                return true;
            case R.id.action_logOut:
                openLogout();
                return true;
            case R.id.action_volver:
                Volver();
                return true;
            case R.id.action_nueva_actividad:
                //openMantenimientos();
                callNuevaActividad();
                Toast.makeText(getApplicationContext(),"GENERAR NUEVA ACTIVIDAD", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_FiltrarTodos:
                //();
                return true;
            case R.id.action_FiltrarHechos:
                //();
                return true;
            case R.id.action_FiltrarPendientes:
                //();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void Volver()
    {

        finish();
        return;


    }
    private void openSearch(MenuItem menu)
    {
        //MenuItem searchItem = menu.findItem(R.id.action_search);
        MenuItem searchItem = menu;
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE); //ESTO REEMPLAZA LA LUPA DEL TECLADO POR OK

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mActividadesAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }
    private void openLogout()
    {
        SessionPrefs.get(this).logOut();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
        return;


    }

    private void callNuevaActividad(){
        startActivity(new Intent(this, InsertNuevaActividadActivity.class));

    }

    @Override
    protected void onResume() {
        super.onResume();





        if(isOnLine()){






            String Token = SessionPrefs.get(this).getToken();
            //
            String nombre = SessionPrefs.get(this).getNombreEmpleado();
            getSupportActionBar().setTitle("User: "+ nombre);
            loadActividades(Token);
        }
        else
        {
            Toast.makeText(getApplicationContext(),"Sin conexion a Internet", Toast.LENGTH_SHORT).show();
        }
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

    public String loadActividades( String token) {

        //Recuperando los datos que vienen como parametro DESDE AUTOS
        //ESTO ES PORQUE ESTA ACTIVITY LA VOY A USAR PARA LISTAR TODAS LAS ACTIVIDADES
        //O LISTAR ACTIVIDADES POR AUTO
        ArrayList<String> datos_origen_llamada = (ArrayList<String>) getIntent().getSerializableExtra("ORIGEN_LLAMADO_MANTENIMIENTO");

        modoLlamado=datos_origen_llamada.get(0);
        Integer valor2 = Integer.valueOf(modoLlamado);

        Call<List<ActividadesDisplayList>> call=null;
        if (valor2==1){
            //es llamado desde el menu general de la pantalla de listado de autos
            //call = mApi.getActividades("Bearer " + token);
            String ubicacionId = SessionPrefs.get(getApplicationContext()).getUbicacionIdEmpleado();
            call= mApi.getActividadessxUbicacion("Bearer " + token, ubicacionId);

        }
        else
        if (valor2==2){
            //si es llamado a nivel de linea, entra a buscar el id del auto
            autoIdOrigenLlamado=datos_origen_llamada.get(1);
            call = mApi.getActividadesXAuto("Bearer " + token,autoIdOrigenLlamado);

        }



        //Call<List<ActividadesDisplayList>> call = mApi.getActividades("Bearer " + token);
        call.enqueue(new Callback<List<ActividadesDisplayList>>(){


            @Override
            public void onResponse(Call<List<ActividadesDisplayList>> call, Response<List<ActividadesDisplayList>> response) {


                if (!response.isSuccessful()) {
                    // TODO: Procesar error de API
                    Toast.makeText(getApplicationContext(),"Token Vencido: Logout + Login", Toast.LENGTH_SHORT).show();
                    progressBarActividades.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(),"ERROR 1", Toast.LENGTH_SHORT).show();

                    return;
                    //volver a pantalla de login


                }
                else
                {

                    //Toast.makeText(getApplicationContext(),"Get Actividades Exitoso", Toast.LENGTH_SHORT).show();
                }

                final List<ActividadesDisplayList> serverActividades = response.body();
                if (serverActividades.size() > 0) {

                    mActividadesAdapter = new ActividadesAdapter(getApplicationContext(), serverActividades);
                    recyclerViewActividades.setAdapter(mActividadesAdapter);

                    //============================
                    //Capturando el click que se hacen en el listado de actividades
                    mActividadesAdapter.setOnItemClickListener(new ActividadesAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {

                            //Click sobre el registro
                            //=====================================================================
                            ArrayList<String> milista = new ArrayList<String>();
                            //parametros que voy a pasar al activy de Detail Auto
                            milista.add(serverActividades.get(position).getActividadID());       //0
                            milista.add(serverActividades.get(position).getactividadAutoPatente());  //1
                            milista.add(serverActividades.get(position).getactividadServicioNombre());   //2
                            milista.add(serverActividades.get(position).getactividadKmTablero());    //3
                            milista.add(serverActividades.get(position).getactividadKmExcedido()); //4
                            milista.add(serverActividades.get(position).getempleadoID());//5
                            milista.add(serverActividades.get(position).getservicioID());//6
                            milista.add(serverActividades.get(position).getubicacionID());//7
                            milista.add(serverActividades.get(position).getactividadEstado().toString());//8
                            milista.add(serverActividades.get(position).getactividadNotaAdicional());//9
                            milista.add(serverActividades.get(position).getautoID());//10
                            milista.add(serverActividades.get(position).getactividadEmpleadoNombre());//11

                            Intent intent = new Intent(getApplicationContext(), DetalleActividadActivity.class);
                            intent.putExtra("DATOS_ACTIVIDAD", milista);
                            startActivity(intent);
                            //=====================================================================


                        }



                    });

                    progressBarActividades.setVisibility(View.INVISIBLE);


                }

            }

            @Override
            public void onFailure(Call<List<ActividadesDisplayList>> call, Throwable t) {
                progressBarActividades.setVisibility(View.INVISIBLE);
                Toast.makeText(getApplicationContext(),"ERROR 2", Toast.LENGTH_SHORT).show();

            }
        });


        return "";
    }


    }