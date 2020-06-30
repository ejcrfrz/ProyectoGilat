package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class InfoActivity extends AppCompatActivity {
    String nombre_incidencia = "";
    String flag = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre_incidencia = extras.getString("nombre_incidencia");
            flag = extras.getString("flag");
            //descripcion = extras.getString("descripcion");
            //foto = extras.getString("foto");
            //ubicacion = extras.getString("ubicacion");
            //comentario = extras.getString("comentario");

        }

        TextView textViewverNombre = findViewById(R.id.verNombre);
       // TextView textViewverDescripcion = findViewById(R.id.verDescripcion);
        //TextView textViewverUbicacion = findViewById(R.id.verUbicacion);
        //TextView textViewverFoto = findViewById(R.id.verFoto);
        //TextView textViewverEstado = findViewById(R.id.verEstado);
        //TextView textViewverComentario = findViewById(R.id.verComentario);


        textViewverNombre.setText(nombre_incidencia);
        //textViewverDescripcion.setText(descripcion);
        //textViewverUbicacion.setText(ubicacion);
        //textViewverFoto.setText(foto);
        //textViewverEstado.setText(estado);
        //textViewverComentario.setText(comentario);

    }


    public void Volver(View view){

        Intent intent1 =new Intent(InfoActivity.this,MainActivity.class);
        intent1.putExtra("flag",flag);
        startActivity(intent1);

        finish();

    }
}