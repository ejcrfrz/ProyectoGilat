package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.gilat.proyectogilat.Entidades.ConfAntena;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgregarActivity extends AppCompatActivity {
    String flag="SI";
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);
        databaseReference = FirebaseDatabase.getInstance().getReference("ConfAntena");


    }


    public void Volver(View view){

        Intent intent1 =new Intent(AgregarActivity.this,AdmiMainActivity.class);

        intent1.putExtra("flag",flag);

        startActivity(intent1);

        finish();

    }
    public void agregar(View view){

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


        String id = databaseReference.push().getKey();
        ConfAntena confAntena = new ConfAntena();
         confAntena.setId(id);
         confAntena.setFlag(flag);
         confAntena.setPotenciaRt(potx_1);
         confAntena.setPotenciaRx(potrx_1);
         confAntena.setPolarizacion(pola_1);
         confAntena.setLongitud(Double.parseDouble(longitud_1));
         confAntena.setLatitud(Double.parseDouble(latitud_1));
         confAntena.setFrecuencia(frec_1);
         confAntena.setNombre(nombre_1);
         databaseReference.child(id).setValue(confAntena);



        Intent intent1 =new Intent(AgregarActivity.this,AdmiMainActivity.class);

        intent1.putExtra("flag",flag);

        startActivity(intent1);

        finish();

    }

}