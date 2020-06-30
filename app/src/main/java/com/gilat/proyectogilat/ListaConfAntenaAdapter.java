package com.gilat.proyectogilat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilat.proyectogilat.Entidades.ConfAntena;

public class ListaConfAntenaAdapter extends RecyclerView.Adapter<ListaConfAntenaAdapter.ConfsAntenaViewHolder> {

    ConfAntena[] lista;
    Context contexto;

    public ListaConfAntenaAdapter(ConfAntena[] lista, Context c) {

        this.lista = lista;
        this.contexto = c;
    }


    public static class ConfsAntenaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nombre_incidencia;
        TextView flag;
        //TextView descripcion;
        //TextView ubicacion;
        //TextView foto;
        //TextView comentario;
        //TextView idAccidente;
        //buttons
        Button buttonVerDetalle;
        //Button buttonHecho;

        Context context;

        public ConfsAntenaViewHolder(View itemView) {
            super(itemView);

            // inicializar contexto
            context = itemView.getContext();
            nombre_incidencia = itemView.findViewById(R.id.nombre_incidencia);
            flag = itemView.findViewById(R.id.flag);
            buttonVerDetalle = itemView.findViewById(R.id.buttonInfo);
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

            buttonVerDetalle.setOnClickListener(this);
            /*
            buttonVerDetalle.setOnClickListener(this);
            buttonHecho.setOnClickListener(this);
            */
        }

        //---------------------------------------------------------------------------

        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.buttonInfo:

                    Intent intent = new Intent(context, InfoActivity.class);
                    intent.putExtra("nombre_incidencia", nombre_incidencia.getText());
                    intent.putExtra("flag", flag.getText());
                    //intent.putExtra("descripcion", descripcion.getText());
                    //intent.putExtra("foto", foto.getText());
                    //intent.putExtra("ubicacion", ubicacion.getText());
                    //intent.putExtra("comentario", comentario.getText());

                    //intent.putExtra("listatrabajos",listTrabajos);
                    context.startActivity(intent);
                    break;
            /*
                case R.id.buttonHecho:
                    Intent intent1 = new Intent(context, AgregarComentarioActivity.class);
                    intent1.putExtra("idAccidente", idAccidente.getText());
                    context.startActivity(intent1);
                    break;
            */
            }
        }
        //--------------------------------------------------------------------------

    }


    @NonNull
    @Override
    public ConfsAntenaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(contexto).inflate(R.layout.rv_conf, parent, false);
        ConfsAntenaViewHolder confsAntenaViewHolder = new ConfsAntenaViewHolder(item);
        return confsAntenaViewHolder;
    }

    @Override
    public void onBindViewHolder(ConfsAntenaViewHolder holder, int position) {
        ConfAntena incidencia = lista[position];
        String getnomb_accidente = incidencia.getNombre();
        String getflag = incidencia.getFlag();
        /*
        String getestado = incidencia.getEstado();
        String getdescripcion = incidencia.getDescripcion();
        String getfoto = incidencia.getFoto();
        String getubicacion = incidencia.getUbicacion();
        String getidaccidente = String.valueOf(incidencia.getIdaccidentes());

         */
        holder.nombre_incidencia.setText(getnomb_accidente);
        holder.flag.setText(getflag);
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

        return lista.length;
    }


}
