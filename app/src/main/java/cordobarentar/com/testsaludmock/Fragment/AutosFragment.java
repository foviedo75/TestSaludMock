package cordobarentar.com.testsaludmock.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import java.util.List;

import cordobarentar.com.testsaludmock.Adapters.AutosAdapterparaNuevaActividad;
import cordobarentar.com.testsaludmock.Api.Api;
import cordobarentar.com.testsaludmock.Api.AuxCallApi;
import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.R;
import cordobarentar.com.testsaludmock.pref.SessionPrefs;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AutosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AutosFragment extends Fragment {

    private Api mApi;
    //String nombre = SessionPrefs.get(getContext()).getNombreEmpleado();
    //getSupportActionBar().setTitle("User: "+ nombre);
    private RecyclerView recyclerViewAutosNuevaActividad;
    private AutosAdapterparaNuevaActividad mAutosAdapterparaNuevaActividad;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AutosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AutosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AutosFragment newInstance(String param1, String param2) {
        AutosFragment fragment = new AutosFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_autos, container, false);
        recyclerViewAutosNuevaActividad = view.findViewById(R.id.recyclerViewAutosNuevaActividad);
        mApi = AuxCallApi.apiService();

        cargarDatos();



        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu,  MenuInflater inflater) {

        inflater.inflate(R.menu.auto_menu_nueva_actividad, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search_nueva_actividad_auto:
                openSearch(item);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
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
                mAutosAdapterparaNuevaActividad.getFilter().filter(newText);
                return false;
            }
        });
    }



    public void cargarDatos(){


        recyclerViewAutosNuevaActividad.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManagerAutos = new LinearLayoutManager(getContext());
        linearLayoutManagerAutos.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewAutosNuevaActividad.setLayoutManager(linearLayoutManagerAutos);

        String ubicacionId = SessionPrefs.get(getContext()).getUbicacionIdEmpleado();
        String token= SessionPrefs.get(getContext()).getToken();
        Call<List<AutosDisplayList>> call = mApi.getAutosxUbicacion("Bearer " + token, ubicacionId);
        call.enqueue(new Callback<List<AutosDisplayList>>(){


            @Override
            public void onResponse(Call<List<AutosDisplayList>> call, Response<List<AutosDisplayList>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(),"Token Vencido: Logout + Login", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getContext(),"Get Autos Exitoso", Toast.LENGTH_SHORT).show();
                    final List<AutosDisplayList> serverAutos = response.body();
                    if (serverAutos.size() > 0) {

                        mAutosAdapterparaNuevaActividad = new AutosAdapterparaNuevaActividad(getContext(), serverAutos);
                        recyclerViewAutosNuevaActividad.setAdapter(mAutosAdapterparaNuevaActividad);
                    }


                }
            }

            @Override
            public void onFailure(Call<List<AutosDisplayList>> call, Throwable t) {
                Toast.makeText(getContext(),t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}