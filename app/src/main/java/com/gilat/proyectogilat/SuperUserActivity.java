package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.gilat.proyectogilat.Entidades.ConfAntena;
import com.gilat.proyectogilat.Entidades.Usuario;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SuperUserActivity extends AppCompatActivity {
    //RECIBE DEL LOGIN
    String flag = "NO";
    GoogleSignInAccount signInAccount;
    //VALORES FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String email_db;
    String pass_db;
    private final int MY_PERMISSIONS = 1;

    //VALORES DIALOG
    Button button_dialog;
    Button button_close;
    Button button_logout;
    TextView editText_name;
    TextView editText_email;


    //VALORES NAVIEW
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    //VALORES BUSCADOR
    SearchView search;
    ListUserAdapter listaConfAntenaAdapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_super_user);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent2 = getIntent();
        flag = intent2.getStringExtra("flag");


        //----------------------------------------------------------------------------------------------------------------------
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        //----------------------------------------------------------------------------------------------------------------------
        SearchView et = (SearchView) findViewById(R.id.buscador);
        //et.setHintTextColor(Color.GRAY);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view1);
        toolbar = findViewById(R.id.toolbar1);

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        //navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.nav_mapa:
                        Intent i = new Intent(SuperUserActivity.this, MapaActivity.class);
                        i.putExtra("flag", flag);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_root:
                        break;
                    case R.id.nav_lista:
                        Intent i2 = new Intent(SuperUserActivity.this, AdmiMainActivity.class);
                        i2.putExtra("flag", flag);
                        startActivity(i2);
                        finish();
                        break;
                    case R.id.nav_face:
                        Intent inte = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/"));
                        startActivity(inte);
                        break;
                    case R.id.nav_twi:
                        Intent inte2 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/"));
                        startActivity(inte2);
                        break;

                }
                return true;
            }
        });
        navigationView.setCheckedItem(R.id.nav_root);
        getUserinfo();
        readData(new MyCallback() {
            @Override
            public void onCallback(List<Usuario> list) {
                Log.d("TAG", String.valueOf(list.size()));
                List<Usuario> listanueva = new ArrayList<>();
                listanueva = list;
                listaConfAntenaAdapter1 = new ListUserAdapter(listanueva, SuperUserActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recyclerView1);
                recyclerView.setAdapter(listaConfAntenaAdapter1);
                recyclerView.setLayoutManager(new LinearLayoutManager(SuperUserActivity.this));

            }
        });

    }

    public interface MyCallback {
        void onCallback(List<Usuario> list);
    }

    private void readData(final MyCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Usuario> lista = new ArrayList<>();


                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    Usuario c1 = child.getValue(Usuario.class);
                    if (!c1.getEmail().equals(email_db) || !c1.getPass().equals(pass_db)) {
                        lista.add(c1);
                    }

                }


                //String size = String.valueOf(lista.length);
                //Log.d("size", size);
                myCallback.onCallback(lista);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


    private void getUserinfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    email_db = dataSnapshot.child("email").getValue().toString();
                    pass_db = dataSnapshot.child("pass").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }


}