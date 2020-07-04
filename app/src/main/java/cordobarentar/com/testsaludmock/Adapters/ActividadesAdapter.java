package cordobarentar.com.testsaludmock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import cordobarentar.com.testsaludmock.POJO.ActividadesDisplayList;

import cordobarentar.com.testsaludmock.R;

public class ActividadesAdapter extends RecyclerView.Adapter<ActividadesAdapter.ViewHolder> implements Filterable {
    private List<ActividadesDisplayList> actividadesItems;
    private List<ActividadesDisplayList> actividadesItemsFull; //ESTA SEGUNDA LISTA ES PARA EL SEARCH

    private Context mContext;
    private ActividadesAdapter.OnItemClickListener mListener;
    public interface OnItemClickListener{

        void onItemClick(int position);

    }
    public void setOnItemClickListener(ActividadesAdapter.OnItemClickListener listener){
        mListener = listener;
    }

    //======================================================================
    //Begin implementando Filtro
    @Override
    public Filter getFilter() {
        return actividadFilter;
    }
    private Filter actividadFilter = new Filter() {
        //ESTE CORRE EN UN HILO EN BACKGROUND
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ActividadesDisplayList> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length()==0){
                filteredList.addAll(actividadesItemsFull);
            }
            else
            {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ActividadesDisplayList item : actividadesItemsFull ){
                    if(item.getactividadAutoPatente().toLowerCase().contains (filterPattern)
                            || item.getactividadServicioNombre().toLowerCase().contains(filterPattern)){

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
            actividadesItems.clear();
            actividadesItems.addAll((List)results.values);
            notifyDataSetChanged();

        }
    };
    //End implementando Filtro
    //======================================================================

    //creando el constructor
    public ActividadesAdapter(Context context, List<ActividadesDisplayList> items) {
        actividadesItems = items;
        mContext = context;
        //prueba searchbar:
        actividadesItemsFull = new ArrayList<>(actividadesItems);
    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                //.inflate(R.layout.items_auto  ,parent,false);
                .inflate(R.layout.items_mantenimiento  ,parent,false);

        ActividadesAdapter.ViewHolder viewHolder = new ActividadesAdapter.ViewHolder(itemView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtPatenteActividades.setText(actividadesItems.get(position).getactividadAutoPatente());


        String notaAdicionales = actividadesItems.get(position).getactividadNotaAdicional();
        if (notaAdicionales == null)
        {
            notaAdicionales = "";
        }
        else notaAdicionales = "--"+ notaAdicionales;


        holder.txtDescripcionActividades.setText(  actividadesItems.get(position).getactividadServicioNombre()  +
                notaAdicionales);
        holder.txtKilometrosTableroActividades.setText(actividadesItems.get(position).getactividadKmTablero()+" Kms");


        String dia = actividadesItems.get(position).getactividadFecha().substring(8,10);
        String mes = actividadesItems.get(position).getactividadFecha().substring(5,7);
        String ano = actividadesItems.get(position).getactividadFecha().substring(0,4);
       // holder.txtFechaActividades.setText(   actividadesItems.get(position).getactividadFecha().substring(0,10));
        holder.txtFechaActividades.setText(   dia + "/"+ mes+ "/"+ano);


        if(actividadesItems.get(position).getactividadEstado()==true)
        {
            holder.txtEmpleadoResponsable.setText(actividadesItems.get(position).getactividadEmpleadoNombre());
            holder.mImageView.setImageResource(R.drawable.iconfinder_bienhecho);
        }
        else
        {
            holder.txtEmpleadoResponsable.setText("");
            holder.mImageView.setImageResource(R.drawable.iconfinder_noquierover);
        }

        //imagenListActividades

    }

    @Override
    public int getItemCount() {
        return actividadesItems.size();
    }







    public void actualizarItems(List<ActividadesDisplayList> actividadesDisplayLists) {
        if (actividadesDisplayLists == null) {
            actividadesItems = new ArrayList<>(0);
        } else {
            actividadesItems = actividadesDisplayLists;
        }
        notifyDataSetChanged();
    }




    //Aca creo el ViewHolder para el adapter

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txtPatenteActividades;
        TextView txtDescripcionActividades;
        TextView txtKilometrosTableroActividades;
        TextView txtFechaActividades;
        TextView txtEmpleadoResponsable;
        ImageView mImageView;
        TextView txtModeloAutoActividad;


        public ViewHolder(View item, final ActividadesAdapter.OnItemClickListener listener){
            super(item);
            txtPatenteActividades = (TextView) item.findViewById(R.id.txtPatenteNuevaActividad);
            txtDescripcionActividades = (TextView)item.findViewById(R.id.txtModeloNuevaActividad);
            txtKilometrosTableroActividades= (TextView)item.findViewById(R.id.txtKilometrosTableroActividades);
            txtFechaActividades= (TextView)item.findViewById(R.id.txtFechaActividades);
            txtEmpleadoResponsable = (TextView)item.findViewById(R.id.txtEmpleadoResponsable);
            mImageView = (ImageView) item.findViewById(R.id.imagenListActividades);
            //txtModeloAutoActividad= (TextView) item.findViewById(R.id.txtModeloAutoActividad);


            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            listener.onItemClick(position);

                        }
                    }


                }
            });

        }
    }




}
