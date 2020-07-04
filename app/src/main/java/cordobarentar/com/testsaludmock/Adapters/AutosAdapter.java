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

import cordobarentar.com.testsaludmock.POJO.AutosDisplayList;
import cordobarentar.com.testsaludmock.R;

public class AutosAdapter extends RecyclerView.Adapter<AutosAdapter.ViewHolder> implements Filterable {


    private List<AutosDisplayList> autoItems;
    private List<AutosDisplayList> autoItemsFull; //ESTA SEGUNDA LISTA ES PARA EL SEARCH


    private Context mContext;
    private OnItemClickListener mListener;
    public interface OnItemClickListener{

        void onItemClick(int position);
        void onLavadoClick(int position);
        void onMantenimientoClick(int position);
        void onFotoClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        mListener = listener;
    }




    //creando el constructor
    public AutosAdapter(Context context, List<AutosDisplayList> items) {
        autoItems = items;
        mContext = context;
        //prueba searchbar:
        autoItemsFull = new ArrayList<>(autoItems);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())

                .inflate(R.layout.items_auto2  ,parent,false);

        ViewHolder viewHolder = new ViewHolder(itemView, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtPatente.setText(autoItems.get(position).getAutoPatente());
        holder.txtModelo.setText(autoItems.get(position).getAutoModelo());
        holder.txtKilometrosTablero.setText(autoItems.get(position).getAutoKmtablero()+" Kms");
        if(autoItems.get(position).getautoActividadesPendientesYN()==true)
        {
            //mostrar icono de actividades pendientes(herramienta)
            holder.mImageView.setImageResource(R.drawable.ic_mantenimiento);
        }
        else
        {
            //mostrar icono de que esta OK(SIN ACTIVIDADES PENDIENTES)
            holder.mImageView.setImageResource(R.drawable.ic_check);
        }
       holder.mImageViewLavado.setImageResource(R.drawable.ic_car_wash);
        holder.mImageFoto.setImageResource(R.drawable.ic_camera);
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
        TextView txtKilometrosTablero;
        ImageView mImageView;
        ImageView mImageViewLavado;
        ImageView mImageFoto;

        public ViewHolder(View item, final OnItemClickListener listener){
            super(item);
            /*txtPatente = (TextView) item.findViewById(R.id.txtPatente);
            txtModelo = (TextView) item.findViewById(R.id.txtModelo);
            txtKilometrosTablero = (TextView) item.findViewById(R.id.txtKilometrosTablero);*/

            txtPatente = (TextView) item.findViewById(R.id.txtPatenteNuevaActividad);
            txtModelo = (TextView) item.findViewById(R.id.txtModeloNuevaActividad);
            txtKilometrosTablero = (TextView) item.findViewById(R.id.txtKilometrosTableroActividades);
            mImageView = (ImageView) item.findViewById(R.id.imagenList);
            mImageViewLavado=(ImageView) item.findViewById(R.id.imagenLavado);
            mImageFoto = (ImageView) item.findViewById(R.id.imagenFoto);


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

            mImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            //CALL ACTIVITY DE mantenimiento
                            listener.onMantenimientoClick(position);

                        }
                    }


                }
            });

            mImageViewLavado.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            //CALL ACTIVITY DE LAVADOS
                            listener.onLavadoClick(position);

                        }
                    }


                }
            });
            mImageFoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null){
                        int position = getAdapterPosition();
                        if (position!= RecyclerView.NO_POSITION){
                            //CALL ACTIVITY DE INSPECCION
                            listener.onFotoClick(position);

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
