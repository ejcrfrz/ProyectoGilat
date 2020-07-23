package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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

    String model="";
    String modul="";
    String ganancia="";
    String region="";
    Spinner spinner;
    String region_1="";
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

            model = extras.getString("model");
            modul = extras.getString("modul");
            region = extras.getString("region");
            ganancia = extras.getString("ganancia");
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

        EditText editTextModel = findViewById(R.id.verModel);
        editTextModel.setText(model);

        EditText editTextGana = findViewById(R.id.verGanancia);
        editTextGana.setText(ganancia);

        EditText editTextModul = findViewById(R.id.verModul);
        editTextModul.setText(modul);

        spinner = findViewById(R.id.verRegion);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.region_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(adapter.getPosition(region));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                region_1 = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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


        EditText editText_model = findViewById(R.id.verModel);
        String model_1 = editText_model.getText().toString();

        EditText editText_modul = findViewById(R.id.verModul);
        String modul_1 = editText_modul.getText().toString();

        EditText editText_gana = findViewById(R.id.verGanancia);
        String gana_1 = editText_gana.getText().toString();

        String reg="wa";


        reference.child(id).child("nombre").setValue(nombre_1);
        reference.child(id).child("frecuencia").setValue(frec_1);
        reference.child(id).child("polarizacion").setValue(pola_1);
        reference.child(id).child("potenciaRt").setValue(potx_1);
        reference.child(id).child("potenciaRx").setValue(potrx_1);
        reference.child(id).child("latitud").setValue(Double.parseDouble(latitud_1));
        reference.child(id).child("longitud").setValue(Double.parseDouble(longitud_1));

        reference.child(id).child("modelAntena").setValue(model_1);
        reference.child(id).child("modulacion").setValue(modul_1);
        reference.child(id).child("ganancia").setValue(gana_1);

        reference.child(id).child("region").setValue(region_1);



        Intent intent3 =new Intent(EditarActivity.this,AdmiMainActivity.class);
        intent3.putExtra("flag",flag);
        startActivity(intent3);

        finish();



    }


}