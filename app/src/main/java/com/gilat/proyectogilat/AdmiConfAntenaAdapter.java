package com.gilat.proyectogilat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilat.proyectogilat.Entidades.ConfAntena;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AdmiConfAntenaAdapter extends RecyclerView.Adapter<AdmiConfAntenaAdapter.ConfsAntenaViewHolder> implements Filterable {
    List<ConfAntena> lista;
    List<ConfAntena> listaAll;

    Context contexto;

    public AdmiConfAntenaAdapter(List<ConfAntena> lista, Context c) {

        this.lista = lista;
        this.contexto = c;
        listaAll = new ArrayList<>(lista);

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ConfAntena> filteredList = new ArrayList<>();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(listaAll);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ConfAntena item : listaAll) {
                    if (item.getNombre().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults filterResults) {
            lista.clear();
            //lista.addAll((Collection<? extends ConfAntena>) filterResults.values);
            lista.addAll((List) filterResults.values);
            //listaAll = (List<ConfAntena>) results.values;
            notifyDataSetChanged();
        }
    };


    public static class ConfsAntenaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre_incidencia;
        TextView flag;
        ImageButton imageButtonInfo;
        TextView letter;
        ImageButton imageButtonEdit;
        ImageButton imageButtonDel;
        String pola;
        String frecuencia;
        double latitud;
        double longitud;
        String potx;
        String potrx;
        String id;
        //TextView descripcion;
        //TextView ubicacion;
        //TextView foto;
        //TextView comentario;
        //TextView idAccidente;
        //buttons

        //Button buttonVerDetalle;
        //Button buttonHecho;

        Context context;

        public ConfsAntenaViewHolder(View itemView) {
            super(itemView);

            // inicializar contexto
            context = itemView.getContext();
            nombre_incidencia = itemView.findViewById(R.id.nombre_incidencia);
            flag = itemView.findViewById(R.id.flag);
            // buttonVerDetalle = itemView.findViewById(R.id.buttonInfo);
            imageButtonInfo = itemView.findViewById(R.id.imageButtonInfo);
            letter = itemView.findViewById(R.id.letter);
            imageButtonEdit = itemView.findViewById(R.id.imageButtonEditar);
            imageButtonDel = itemView.findViewById(R.id.imageButtonBorrar);
            /*
            estado = itemView.findViewById(R.id.estado);
            descripcion = itemView.findViewById(R.id.descripcion);
            foto = itemView.findViewById(R.id.foto);
            ubicacion = itemView.findViewById(R.id.ubicacion);
            comentario = itemView.findViewById(R.id.comentario);
            idAccidente = itemView.findViewById(R.id.idAccidente);
            //Referencia a botones:
            buttonVerDetalle = itemView.findViewById(R.id.buttonDetalle);
            buttonHecho = itemView.findViewById(R.id.buttonHecho);
            */
        }

        void setOnClickListeners() {

            //   buttonVerDetalle.setOnClickListener(this);
            imageButtonInfo.setOnClickListener(this);
            imageButtonEdit.setOnClickListener(this);
            imageButtonDel.setOnClickListener(this);
            /*
            buttonVerDetalle.setOnClickListener(this);
            buttonHecho.setOnClickListener(this);
            */
        }

        //---------------------------------------------------------------------------

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.imageButtonEditar:

                    Intent intent2 = new Intent(context, EditarActivity.class);
                    intent2.putExtra("nombre_incidencia", nombre_incidencia.getText());
                    intent2.putExtra("flag", flag.getText());
                    intent2.putExtra("letter", letter.getText());
                    intent2.putExtra("pola", pola);
                    intent2.putExtra("frecuencia", frecuencia);
                    intent2.putExtra("latitud", latitud);
                    intent2.putExtra("longitud", longitud);
                    intent2.putExtra("potx", potx);
                    intent2.putExtra("potrx", potrx);
                    intent2.putExtra("id", id);

                    context.startActivity(intent2);
                    break;

                case R.id.imageButtonInfo:
                    Intent intent1 = new Intent(context, AdmInfoActivity.class);
                    intent1.putExtra("nombre_incidencia", nombre_incidencia.getText());
                    intent1.putExtra("flag", flag.getText());
                    intent1.putExtra("letter", letter.getText());
                    intent1.putExtra("pola", pola);
                    intent1.putExtra("frecuencia", frecuencia);
                    intent1.putExtra("latitud", latitud);
                    intent1.putExtra("longitud", longitud);
                    intent1.putExtra("potx", potx);
                    intent1.putExtra("potrx", potrx);

                    context.startActivity(intent1);
                    break;
                case R.id.imageButtonBorrar:
                    Intent intent4 = new Intent(context, BorrarActivity.class);
                    intent4.putExtra("flag", flag.getText());
                    intent4.putExtra("id", id);
                    context.startActivity(intent4);

                    break;



            }
        }
        //--------------------------------------------------------------------------

    }


    @NonNull
    @Override
    public AdmiConfAntenaAdapter.ConfsAntenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(contexto).inflate(R.layout.rv_conf_admi, parent, false);
        AdmiConfAntenaAdapter.ConfsAntenaViewHolder confsAntenaViewHolder = new AdmiConfAntenaAdapter.ConfsAntenaViewHolder(item);
        return confsAntenaViewHolder;
    }

    @Override
    public void onBindViewHolder(AdmiConfAntenaAdapter.ConfsAntenaViewHolder holder, int position) {
        ConfAntena incidencia = lista.get(position);
        String getnomb_accidente = incidencia.getNombre();
        String getflag = incidencia.getFlag();
        String getletter = incidencia.getNombre().substring(0,1);
        String getpola = incidencia.getPolarizacion();
        String getfrec = incidencia.getFrecuencia();
        double getlat = incidencia.getLatitud();
        double getlon = incidencia.getLongitud();
        String getpot = incidencia.getPotenciaRt();
        String getpotrx = incidencia.getPotenciaRx();
        String getid = incidencia.getId();
        /*
        String getestado = incidencia.getEstado();
        String getdescripcion = incidencia.getDescripcion();
        String getfoto = incidencia.getFoto();
        String getubicacion = incidencia.getUbicacion();
        String getidaccidente = String.valueOf(incidencia.getIdaccidentes());

         */
        holder.nombre_incidencia.setText(getnomb_accidente);
        holder.flag.setText(getflag);

        holder.letter.setText(getletter);


        List<String> colors;

        colors=new ArrayList<String>();

        colors.add("#5E97F6");
        colors.add("#9CCC65");
        colors.add("#FF8A65");
        colors.add("#9E9E9E");
        colors.add("#9FA8DA");
        colors.add("#90A4AE");
        colors.add("#AED581");
        colors.add("#F6BF26");
        colors.add("#FFA726");
        colors.add("#4DD0E1");
        colors.add("#BA68C8");
        colors.add("#A1887F");

        Random r = new Random();
        int i1 = r.nextInt(11- 0) + 0;
        GradientDrawable draw = new GradientDrawable();
        draw.setShape(GradientDrawable.OVAL);
        draw.setColor(Color.parseColor(colors.get(i1)));
        holder.letter.setBackground(draw);

        holder.pola = getpola;
        holder.frecuencia = getfrec;
        holder.latitud= getlat;
        holder.longitud = getlon;
        holder.potx = getpot;
        holder.potrx = getpotrx;
        holder.id = getid;
        /*
        holder.estado.setText(getestado);
        holder.descripcion.setText(getdescripcion);
        holder.foto.setText(getfoto);
        holder.ubicacion.setText(getubicacion);
        holder.idAccidente.setText(getidaccidente);
        */
        holder.setOnClickListeners();

    }

    @Override
    public int getItemCount() {

        return lista.size();
    }


}
