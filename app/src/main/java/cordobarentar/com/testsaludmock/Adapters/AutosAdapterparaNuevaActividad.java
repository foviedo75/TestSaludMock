package cordobarentar.com.testsaludmock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.R;

public class AutosAdapterparaNuevaActividad extends RecyclerView.Adapter<AutosAdapterparaNuevaActividad.ViewHolder> implements Filterable {


    private List<AutosDisplayList> autoItems;
    private List<AutosDisplayList> autoItemsFull; //ESTA SEGUNDA LISTA ES PARA EL SEARCH


    private Context mContext;
    private OnItemClickListener mListener2;
    public interface OnItemClickListener{

        void onItemClick(int position);

    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener2 = listener;
    }

    //creando el constructor
    public AutosAdapterparaNuevaActividad(Context context, List<AutosDisplayList> items) {
        autoItems = items;
        mContext = context;
        autoItemsFull = new ArrayList<>(autoItems);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.items_auto_para_nueva_actividad  ,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemView,null);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtPatente.setText(autoItems.get(position).getAutoPatente());
        holder.txtModelo.setText(autoItems.get(position).getAutoModelo());

    }

    @Override
    public int getItemCount() {
        return autoItems.size();
    }


    public void actualizarItems(List<AutosDisplayList> autosDisplayLists) {
        if (autosDisplayLists == null) {
            autoItems = new ArrayList<>(0);
        } else {
            autoItems = autosDisplayLists;
        }
        notifyDataSetChanged();
    }




    //Aca creo el ViewHolder para el adapter

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPatente;
        TextView txtModelo;

        public ViewHolder(View item, final AdapterView.OnItemClickListener listener){
            super(item);

            txtPatente = (TextView) item.findViewById(R.id.txtPatenteNuevaActividad);
            txtModelo = (TextView) item.findViewById(R.id.txtModeloNuevaActividad);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            //listener.onItemClick(position);

                        }
                    }


                }
            });

        }
    }

    //======================================================================
    //Begin implementando Filtro
    @Override
    public Filter getFilter() {
        return autoFiler;
    }

    private Filter autoFiler = new Filter() {
        //ESTE CORRE EN UN HILO EN BACKGROUND
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<AutosDisplayList> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(autoItemsFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (AutosDisplayList item : autoItemsFull ){
                    if(item.getAutoPatente().toLowerCase().contains (filterPattern)
                            || item.getAutoModelo().toLowerCase().contains(filterPattern)){

                        filteredList.add(item);
                    }
                }

            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        //este corre en el hilo del UI
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            autoItems.clear();
            autoItems.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
    //End implementando Filtro
    //======================================================================



}
