package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarActivity extends AppCompatActivity {
    String nombre_incidencia = "";
    String flag = "SI";
    String pola="";
    String frecuencia ="";
    double latitud =0.0;
    double longitud=0.0;
    String potx="";
    String potrx="";
    String id="";
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);
        reference = FirebaseDatabase.getInstance().getReference("ConfAntena");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            nombre_incidencia = extras.getString("nombre_incidencia");
            flag = extras.getString("flag");
            pola = extras.getString("pola");
            frecuencia = extras.getString("frecuencia");
            latitud = extras.getDouble("latitud");
            longitud = extras.getDouble("longitud");
            potx = extras.getString("potx");
            potrx = extras.getString("potrx");
            id = extras.getString("id");
        }

        EditText textViewverNombre = findViewById(R.id.verNombre);
        textViewverNombre.setText(nombre_incidencia);

        EditText textViewverPola = findViewById(R.id.verPola);
        textViewverPola.setText(pola);

        EditText textViewverFrec = findViewById(R.id.verFrec);
        textViewverFrec.setText(frecuencia);

        EditText textViewverPotx = findViewById(R.id.verPotx);
        textViewverPotx.setText(potx);

        EditText textViewverPotrx = findViewById(R.id.verPotrx);
        textViewverPotrx.setText(potrx);

        EditText editTextLatitud = findViewById(R.id.verLatitud);
        editTextLatitud.setText(String.valueOf(latitud));

        EditText editTextLongitud = findViewById(R.id.verLongitud);
        editTextLongitud.setText(String.valueOf(longitud));



    }

    public void Volver(View view){

        Intent intent1 =new Intent(EditarActivity.this,AdmiMainActivity.class);
        intent1.putExtra("flag",flag);
        startActivity(intent1);

        finish();

    }

    public void guardar(View view){

        EditText editText_nombre = findViewById(R.id.verNombre);
        String nombre_1 = editText_nombre.getText().toString();

        EditText editText_frec = findViewById(R.id.verFrec);
        String frec_1 = editText_frec.getText().toString();

        EditText editText_pola = findViewById(R.id.verPola);
        String pola_1 = editText_pola.getText().toString();

        EditText editText_potx = findViewById(R.id.verPotx);
        String potx_1 = editText_potx.getText().toString();

        EditText editText_potrx = findViewById(R.id.verPotrx);
        String potrx_1 = editText_potrx.getText().toString();

        EditText editText_latitud = findViewById(R.id.verLatitud);
        String latitud_1 = editText_latitud.getText().toString();

        EditText editText_longitud = findViewById(R.id.verLongitud);
        String longitud_1 = editText_longitud.getText().toString();

        reference.child(id).child("nombre").setValue(nombre_1);
        reference.child(id).child("frecuencia").setValue(frec_1);
        reference.child(id).child("polarizacion").setValue(pola_1);
        reference.child(id).child("potenciaRt").setValue(potx_1);
        reference.child(id).child("potenciaRx").setValue(potrx_1);
        reference.child(id).child("latitud").setValue(Double.parseDouble(latitud_1));
        reference.child(id).child("longitud").setValue(Double.parseDouble(longitud_1));

        Intent intent3 =new Intent(EditarActivity.this,AdmiMainActivity.class);
        intent3.putExtra("flag",flag);
        startActivity(intent3);

        finish();



    }


}