package com.gilat.proyectogilat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CambioActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    String flag="SI";
    String id="";
    String re="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambio);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getString("flag");

            id = extras.getString("id");
            re = extras.getString("re");
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        databaseReference.child(id).child("admi").setValue(re);

        Intent intent1 =new Intent(CambioActivity.this,SuperUserActivity.class);

        intent1.putExtra("flag",flag);

        startActivity(intent1);

        finish();



    }
}