package cordobarentar.com.testsaludmock;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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

import cordobarentar.com.testsaludmock.Adapters.AutosAdapter;
import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AutosActivity extends AppCompatActivity {

    TextView txtUserLogueado;
    //private Retrofit mRestAdapter;
    private Api mApi;
    private RecyclerView recyclerViewAutos;
    private AutosAdapter mAutosAdapter;
    ProgressBar progressBarAutos;
    private boolean mIsLoggedIn = false; //este flag ayudara a determinar si el usuario esta o no logueado

    private static final int REQUEST_CODE = 1;  //SE LE PUEDE PONER CUALQUIER VALOR
    SwipeRefreshLayout swipeRefreshLayout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autos);

        txtUserLogueado = (TextView) findViewById(R.id.txtUserLogueado);
        recyclerViewAutos =(RecyclerView) findViewById(R.id.recyclerViewListaAutos);
        recyclerViewAutos.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerAutos = new LinearLayoutManager(getApplicationContext());
        linearLayoutManagerAutos.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAutos.setLayoutManager(linearLayoutManagerAutos);
        progressBarAutos = (ProgressBar)findViewById(R.id.progressBarAutos);
        progressBarAutos.setVisibility(View.VISIBLE);



        // Redirección al Login en caso de no estar logueado
        if (!SessionPrefs.get(this).isLoggedIn()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }


        mApi = AuxCallApi.apiService();
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshAutos);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                String Token = SessionPrefs.get(getApplicationContext()).getToken();
                loadAutos(Token);
                mAutosAdapter.actualizarItems(null);
                swipeRefreshLayout.setRefreshing(false);


            }
        });



    }
    //=====================================================
    //para el search view
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.auto_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_nueva_actividad_auto:
                openSearch(item);
                return true;
            case R.id.action_logOut:
                openLogout();
                return true;
            case R.id.action_mantenimiento:
                openMantenimientos();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openMantenimientos(){
        //LLAMAR A PANTALLA DE MANTENIMIENTOS
        /*Intent intent = new Intent(getApplicationContext(), ActividadesActivity.class);
        startActivity(intent);*/

        ArrayList<String> milista = new ArrayList<String>();
        //parametros que voy a pasar al activy de Detail Auto
        milista.add("1");       //0

        Intent intent = new Intent(getApplicationContext(), ActividadesActivity.class);
        intent.putExtra("ORIGEN_LLAMADO_MANTENIMIENTO", milista);
        startActivity(intent);


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
                mAutosAdapter.getFilter().filter(newText);
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

    //=====================================================

    @Override
    protected void onResume() {
        super.onResume();

        if(isOnLine()){
            String Token = SessionPrefs.get(this).getToken();
            //
            txtUserLogueado.setText(SessionPrefs.get(this).getNombreEmpleado());
            String nombre = SessionPrefs.get(this).getNombreEmpleado();

            getSupportActionBar().setTitle("User: "+ nombre);
            loadAutos(Token);
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

    public String loadAutos( String token) {


        //Call<List<AutosDisplayList>> call = mApi.getAutos("Bearer " + token);
        //llamo a la api para que solo traiga los autos de la ubicacion del empleado
        String ubicacionId = SessionPrefs.get(getApplicationContext()).getUbicacionIdEmpleado();
        Call<List<AutosDisplayList>> call = mApi.getAutosxUbicacion("Bearer " + token, ubicacionId);
        call.enqueue(new Callback<List<AutosDisplayList>>() {
            @Override
            public void onResponse(Call<List<AutosDisplayList>> call,
                                   Response<List<AutosDisplayList>> response) {
                if (!response.isSuccessful()) {
                    // TODO: Procesar error de API
                    Toast.makeText(getApplicationContext(),"Token Vencido: Logout + Login", Toast.LENGTH_SHORT).show();
                    progressBarAutos.setVisibility(View.INVISIBLE);
                    txtUserLogueado.setVisibility(View.INVISIBLE);
                    return;
                    //volver a pantalla de login
                }
                else
                {

                    //Toast.makeText(getApplicationContext(),"Get Autos Exitoso", Toast.LENGTH_SHORT).show();
                }

                final List<AutosDisplayList> serverAutos = response.body();
                if (serverAutos.size() > 0) {


                    mAutosAdapter = new AutosAdapter(getApplicationContext(), serverAutos);
                    recyclerViewAutos.setAdapter(mAutosAdapter);

                    //============================
                    //Capturando el click que se hacen en el listado de autos
                    mAutosAdapter.setOnItemClickListener(new AutosAdapter.OnItemClickListener() {
                        @Override
                        public void onItemClick(int position) {
                            //click para actualizar el km
                            showAutos(serverAutos, position);
                        }

                        @Override
                        public void onFotoClick(int position) {

                            Toast.makeText(getApplicationContext(),"INICIAR INSPECCION DE AUTO", Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onLavadoClick(int position) {
                            //=====================================================================
                            ArrayList<String> milista = new ArrayList<String>();
                            //parametros que voy a pasar al activy de Detail Auto
                            milista.add(serverAutos.get(position).getAutoID());       //0
                            milista.add(serverAutos.get(position).getAutoPatente());  //1
                            milista.add(serverAutos.get(position).getAutoModelo());   //2
                            milista.add(serverAutos.get(position).getAutoColor());    //3
                            milista.add(serverAutos.get(position).getAutoKmtablero()); //4
                            milista.add(serverAutos.get(position).getAutoKmBajados()); //5
                            milista.add(serverAutos.get(position).getAutoKmReales());  //6
                            milista.add(serverAutos.get(position).getAutoCodRadio()); //7
                            milista.add(serverAutos.get(position).getAutoTelefono()); //8

                            boolean valorBool=serverAutos.get(position).getautoautoLucesAutomaticasYN();//9
                            if (valorBool == true)
                                milista.add("SI");
                            else milista.add("NO");
                            milista.add(serverAutos.get(position).getUbicacionID()); //10
                            milista.add(serverAutos.get(position).getAutoActivoYN().toString()); //11
                            milista.add(serverAutos.get(position).getautoActividadesPendientesYN().toString()); //12
                            Intent intent = new Intent(getApplicationContext(), Lavado2Activity.class);
                            intent.putExtra("DATOS_AUTO", milista);
                            startActivity(intent);
                            //=====================================================================

                        }

                        @Override
                        public void onMantenimientoClick(int position) {
                            AutosDisplayList B = serverAutos.get(position);
                            String C= B.getAutoPatente();
                            //Toast.makeText(getApplicationContext(),"Mantenimiento en auto "+ C, Toast.LENGTH_SHORT).show();

                            ArrayList<String> milista = new ArrayList<String>();
                            //parametros que voy a pasar al activy de Detail Auto
                            milista.add("2");       //0
                            milista.add(serverAutos.get(position).getAutoID());       //1

                            Intent intent = new Intent(getApplicationContext(), ActividadesActivity.class);
                            intent.putExtra("ORIGEN_LLAMADO_MANTENIMIENTO", milista);
                            startActivity(intent);

                        }
                    });
                    progressBarAutos.setVisibility(View.INVISIBLE);
                    txtUserLogueado.setVisibility(View.INVISIBLE);
                }
            }
            @Override
            public void onFailure(Call<List<AutosDisplayList>> call, Throwable t) {

                Toast.makeText(getApplicationContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });
        return "";
    }



    private void showAutos(List<AutosDisplayList> serverAutos, Integer position) {


        progressBarAutos.setVisibility(View.VISIBLE);
        //Aca vamos a llamar a una nueva activity que permitira modificar el kilometraje del auto seleccionado

        ArrayList<String> milista = new ArrayList<String>();

        //parametros que voy a pasar al activy de Detail Auto
        milista.add(serverAutos.get(position).getAutoID());       //0
        milista.add(serverAutos.get(position).getAutoPatente());  //1
        milista.add(serverAutos.get(position).getAutoModelo());   //2
        milista.add(serverAutos.get(position).getAutoColor());    //3
        milista.add(serverAutos.get(position).getAutoKmtablero()); //4
        milista.add(serverAutos.get(position).getAutoKmBajados()); //5

        milista.add(serverAutos.get(position).getAutoKmReales());  //6

        milista.add(serverAutos.get(position).getAutoCodRadio()); //7
        milista.add(serverAutos.get(position).getAutoTelefono()); //8


        boolean valorBool=serverAutos.get(position).getautoautoLucesAutomaticasYN();//9
        if (valorBool == true)
            milista.add("SI");
        else milista.add("NO");
        milista.add(serverAutos.get(position).getUbicacionID()); //10
        milista.add(serverAutos.get(position).getAutoActivoYN().toString()); //11
        milista.add(serverAutos.get(position).getautoActividadesPendientesYN().toString()); //12

        Bundle bundleOptions = new Bundle();

        Intent intent = new Intent(getApplicationContext(), AutoDetailActivity.class);
        intent.putExtra("DATOS_AUTO", milista);
        //startActivity(intent);    //aca llama a la pantalla donde modifico los kilometros
        startActivityForResult(intent,REQUEST_CODE,bundleOptions);

        progressBarAutos.setVisibility(View.INVISIBLE);



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==REQUEST_CODE){  //esto captura cuando regresa desde la pantalla de modifica el km

            progressBarAutos.setVisibility(View.VISIBLE);
            try {
                Thread.sleep(1000); //lo hago esperar un segundo porque si no no refresca el valor del kilometraje
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            mAutosAdapter.actualizarItems(null);
            String Token = SessionPrefs.get(this).getToken();
            progressBarAutos.setVisibility(View.INVISIBLE);
            loadAutos(Token);

        }


    }
}