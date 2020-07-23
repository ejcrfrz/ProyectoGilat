package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gilat.proyectogilat.Entidades.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    //VALORES LOGIN GOOGLE
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    //VALORES LOGIN
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button buttonLogin;
    DatabaseReference databaseReference;
    private String email = "";
    private String password = "";
    private String admi="";
    private String key="";
    private FirebaseAuth mAuth;

    //VALORES BACKGROUND CAMBIA
    ConstraintLayout constraintLayout;
    TextView tvTimeMsg;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//--------------------------------------------------------------------------------------------------------------------------
        //BACKGROUND CAMBIA POR HORARIO
        constraintLayout = findViewById(R.id.container);
        tvTimeMsg = findViewById(R.id.tv_time_msg);
        Calendar c = Calendar.getInstance();
        int timOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timOfDay >= 0 && timOfDay < 12) {
            constraintLayout.setBackground(getDrawable(R.drawable.buenas_tardes_4));
            tvTimeMsg.setText("Buenos Días");
        } else if (timOfDay >= 12 && timOfDay < 18) {
            constraintLayout.setBackground(getDrawable(R.drawable.buenas_tardes_200));
            tvTimeMsg.setText("Buenas Tardes");

        } else if (timOfDay >= 18 && timOfDay < 24) {
            constraintLayout.setBackground(getDrawable(R.drawable.imagen_noche));
            tvTimeMsg.setText("Buenas Noches");
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//--------------------------------------------------------------------------------------------------------------------------
        //LOGIN
        editTextEmail = (EditText) findViewById(R.id.in_correo);
        editTextPass = (EditText) findViewById(R.id.in_pass);
        buttonLogin = (Button) findViewById(R.id.button_iniciar_a);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = editTextEmail.getText().toString();
                password = editTextPass.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    LoginUser();
                } else {
                    Toast.makeText(LoginActivity.this, "Complete los campos!!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        createRequest();

        findViewById(R.id.button_google).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        findViewById(R.id.recuperar_pass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this, ForgotActivity.class));

            }
        });


    }

    private void createRequest() {

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                // ...
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("flag", "NO");
                            startActivity(intent);


                        } else {
                            Toast.makeText(LoginActivity.this, "Sorry auth failed.", Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    public void LoginUser() {

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = mAuth.getCurrentUser();

                    if (user.isEmailVerified()) {

                        admi = mAuth.getUid();
                        databaseReference.child("Users/" + admi).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Usuario cuack=  dataSnapshot.getValue(Usuario.class);
                                //Log.d("cuack",cuack.getAdmi());

                                assert cuack != null;
                                if (cuack.getAdmi().equals("SI")) {
                                    Intent intent1 = new Intent(LoginActivity.this, AdmiMainActivity.class);
                                    intent1.putExtra("flag", "SI");
                                    startActivity(intent1);


                                } else {
                                    Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                                    intent1.putExtra("flag", "SI");
                                    startActivity(intent1);

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });





                    } else {
                        Toast.makeText(LoginActivity.this, "Correo no verificado!!", Toast.LENGTH_LONG).show();


                    }


                } else {
                    Toast.makeText(LoginActivity.this, "Usuario o Password incorrecto!!", Toast.LENGTH_LONG).show();

                }


            }
        });


    }


    public void Registar(View view) {

        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
        finish();

    }


}
