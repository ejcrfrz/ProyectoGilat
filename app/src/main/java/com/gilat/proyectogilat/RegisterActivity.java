package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextEmail;
    private EditText editTextPass;
    private EditText editTextRePass;
    private Button buttonRegister;

    private String name = "";
    private String email = "";
    private String pass = "";
    private String re_pass = "";

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        editTextName = (EditText) findViewById(R.id.reg_nombre);
        editTextEmail = (EditText) findViewById(R.id.in_correo);
        editTextPass = (EditText) findViewById(R.id.in_pass);
        editTextRePass = (EditText) findViewById(R.id.reg_pass_c);
        buttonRegister = (Button) findViewById(R.id.button_registrar_a);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editTextName.getText().toString();
                email = editTextEmail.getText().toString();
                pass = editTextPass.getText().toString();
                re_pass = editTextRePass.getText().toString();

                if (!name.isEmpty() && !email.isEmpty() && !pass.isEmpty() && !re_pass.isEmpty()) {


                    if (pass.length() >= 6) {
                        registeruser();
                    } else {

                        Toast.makeText(RegisterActivity.this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();

                    }


                } else {

                    Toast.makeText(RegisterActivity.this, "Debe completar los campos", Toast.LENGTH_SHORT).show();

                }


            }
        });


    }


    public void registeruser() {
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {


                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("pass", pass);


                    String id = mAuth.getCurrentUser().getUid();
                    mDatabase.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task2) {
                            if (task2.isSuccessful()) {

                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish();
                            } else {

                                Toast.makeText(RegisterActivity.this, "No se pudo completar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(RegisterActivity.this, "No se puedo pe", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
