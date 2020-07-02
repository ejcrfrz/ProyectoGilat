package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BorrarActivity extends AppCompatActivity {
    String flag="SI";
    String id="";
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrar);

        databaseReference = FirebaseDatabase.getInstance().getReference("ConfAntena");


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getString("flag");

            id = extras.getString("id");

        }

        databaseReference.child(id).setValue(null);
        Intent intent1 =new Intent(BorrarActivity.this,AdmiMainActivity.class);

        intent1.putExtra("flag",flag);

        startActivity(intent1);

        finish();

    }
}