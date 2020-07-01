package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class ForgotActivity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    EditText correo;
    Button send;
    //VALORES BACKGROUND CAMBIA
    ConstraintLayout constraintLayout;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        //--------------------------------------------------------------------------------------------------------------------------
        //BACKGROUND CAMBIA POR HORARIO
        constraintLayout = findViewById(R.id.container);
        Calendar c = Calendar.getInstance();
        int timOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timOfDay >= 0 && timOfDay < 12) {
            constraintLayout.setBackground(getDrawable(R.drawable.buenas_tardes_4));
        } else if (timOfDay >= 12 && timOfDay < 18) {
            constraintLayout.setBackground(getDrawable(R.drawable.buenas_tardes_200));

        }
        else if(timOfDay >= 18 && timOfDay < 24){
            constraintLayout.setBackground(getDrawable(R.drawable.imagen_noche));
        }
        //--------------------------------------------------------------------------------------------------------------------------



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        correo = findViewById(R.id.for_correo);
        send = findViewById(R.id.enviar_pass);

        firebaseAuth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
            firebaseAuth.sendPasswordResetEmail(correo.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(ForgotActivity.this, LoginActivity.class));
                            finish();
                        }
                        else{
                            Toast.makeText(ForgotActivity.this, "No se pudo completar", Toast.LENGTH_SHORT).show();
                        }
                }
            });
            }
        });



    }
}