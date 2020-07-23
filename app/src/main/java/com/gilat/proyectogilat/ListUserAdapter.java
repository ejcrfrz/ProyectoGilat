package com.gilat.proyectogilat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gilat.proyectogilat.Entidades.ConfAntena;
import com.gilat.proyectogilat.Entidades.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ListUserAdapter extends RecyclerView.Adapter<ListUserAdapter.ConfsUserViewHolder>{
    List<Usuario> lista;
    List<Usuario> listaAll;
    //String flag = "NO";
    Context contexto;
    public ListUserAdapter(List<Usuario> lista, Context c) {

        this.lista = lista;
        this.contexto = c;
        listaAll = new ArrayList<>(lista);

    }


    public static class ConfsUserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView user_nombre;
        TextView user_correo;
       // ImageButton imageButtonInfo;
        TextView letter;
        Switch aSwitch;
        String iduser;

        //Button buttonVerDetalle;
        //Button buttonHecho;

        Context context;

        public ConfsUserViewHolder(View itemView) {
            super(itemView);

            // inicializar contexto
            context = itemView.getContext();
            user_nombre = itemView.findViewById(R.id.user_nombre);
            user_correo = itemView.findViewById(R.id.user_correo);
            letter = itemView.findViewById(R.id.letter);
            aSwitch = itemView.findViewById(R.id.switch1);

        }

        void setOnClickListeners() {

            //   buttonVerDetalle.setOnClickListener(this);
            //imageButtonInfo.setOnClickListener(this);
            /*
            buttonVerDetalle.setOnClickListener(this);
            buttonHecho.setOnClickListener(this);
            */
            aSwitch.setOnClickListener(this);

        }

        //---------------------------------------------------------------------------

        @Override
        public void onClick(View view) {

            if(view.getId() == R.id.switch1){
                if(aSwitch.isChecked()){
/*
                    Intent intent1 = new Intent(context, CambioActivity.class);
                    intent1.putExtra("flag", "SI");
                    intent1.putExtra("id",iduser);
                    intent1.putExtra("re","SI");
                    context.startActivity(intent1);
*/
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                    databaseReference.child(iduser).child("admi").setValue("SI");

                }
                else{
/*
                    Intent intent2 = new Intent(context, CambioActivity.class);
                    intent2.putExtra("flag", "SI");
                    intent2.putExtra("id",iduser);
                    intent2.putExtra("re","NO");
                    context.startActivity(intent2);
*/
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

                    databaseReference.child(iduser).child("admi").setValue("NO");
                }

            }



        }
        //--------------------------------------------------------------------------

    }


    @NonNull
    @Override
    public ListUserAdapter.ConfsUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(contexto).inflate(R.layout.rv_user, parent, false);
        ListUserAdapter.ConfsUserViewHolder confsUserViewHolder = new ListUserAdapter.ConfsUserViewHolder(item);


        return confsUserViewHolder;
    }

    @Override
    public void onBindViewHolder(ListUserAdapter.ConfsUserViewHolder holder, int position) {

        Usuario usuario = lista.get(position);
        String getnomb = usuario.getName();
        String getletter = usuario.getName().substring(0,1);
        String getcorreo = usuario.getEmail();
        String getadmi = usuario.getAdmi();
        String getiduser = usuario.getIduser();
        if(getadmi.equals("SI")){
            holder.aSwitch.setChecked(true);
        }
        else{
            holder.aSwitch.setChecked(false);

        }

        holder.user_nombre.setText(getnomb);
        holder.user_correo.setText(getcorreo);
        holder.letter.setText(getletter);
        holder.iduser = getiduser;
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

        holder.setOnClickListeners();

    }

    @Override
    public int getItemCount() {

        return lista.size();
    }





}
