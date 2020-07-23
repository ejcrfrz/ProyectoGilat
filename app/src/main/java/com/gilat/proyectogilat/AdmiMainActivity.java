package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.gilat.proyectogilat.Entidades.ConfAntena;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdmiMainActivity extends AppCompatActivity {
    //RECIBE DEL LOGIN
    String flag = "NO";
    GoogleSignInAccount signInAccount;
    //VALORES FIREBASE
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String Name_DB;
    String Email_DB;
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
    AdmiConfAntenaAdapter listaConfAntenaAdapter;

    //List<ConfAntena> lista = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admi_main);

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
                        Intent i = new Intent(AdmiMainActivity.this, MapaActivity.class);
                        i.putExtra("flag", flag);
                        startActivity(i);
                        finish();
                        break;
                    case R.id.nav_root:
                        Intent i2 = new Intent(AdmiMainActivity.this, SuperUserActivity.class);
                        i2.putExtra("flag", flag);
                        startActivity(i2);

                        break;
                    case R.id.nav_lista:
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
        navigationView.setCheckedItem(R.id.nav_lista);
        //----------------------------------------------------------------------------------------------------------------------
        if (flag.equals("SI")) {
            getUserinfo();
        } else {
            signInAccount = GoogleSignIn.getLastSignedInAccount(this);
        }


        readData(new MainActivity.MyCallback() {
            @Override
            public void onCallback(List<ConfAntena> list) {
                Log.d("TAG", String.valueOf(list.size()));
                List<ConfAntena> listanueva = new ArrayList<>();
                listanueva = list;
                listaConfAntenaAdapter = new AdmiConfAntenaAdapter(listanueva, AdmiMainActivity.this);
                RecyclerView recyclerView = findViewById(R.id.recyclerView);
                recyclerView.setAdapter(listaConfAntenaAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(AdmiMainActivity.this));

            }
        });
        // Log.d("TAG1", String.valueOf(lista.size()));


        //---------------------------------------------------------------------------------------------
        search = findViewById(R.id.buscador);


        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                listaConfAntenaAdapter.getFilter().filter(newText);
                return false;
            }
        });
        //---------------------------------------------------------------------------------------------
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if(position==0){

                    }
                    else if (position==1){

                        String nada = "";
                        listaConfAntenaAdapter.getFilter().filter(nada);
                    }
                    else if (position==2){

                        listaConfAntenaAdapter.getFilter().filter("Huancavelica");
                    }
                    else if (position==3){

                        listaConfAntenaAdapter.getFilter().filter("Ayacucho");

                    }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    public interface MyCallback {
        void onCallback(List<ConfAntena> list);
    }

    private void readData(final MainActivity.MyCallback myCallback) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("ConfAntena").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<ConfAntena> lista = new ArrayList<>();

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    ConfAntena c1 = child.getValue(ConfAntena.class);

                    lista.add(c1);

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


    public void Sitio(View view) {

        Intent i = new Intent(AdmiMainActivity.this, MapsActivity1.class);
        startActivity(i);

    }


    public void showDialog(View view) {

         AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                AdmiMainActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        view = inflater.inflate(R.layout.dialog_sesion, null);
        editText_name = view.findViewById(R.id.dialog_name);
        editText_email = view.findViewById(R.id.dialog_email);
        button_logout = view.findViewById(R.id.dialog_logout);
        button_close = view.findViewById(R.id.dialog_close);
        final AlertDialog alert = alertDialog2.create();
        if (flag.equals("SI")) {
            editText_name.setText(Name_DB);
            editText_email.setText(Email_DB);
        } else {
            editText_name.setText(signInAccount.getDisplayName());
            editText_email.setText(signInAccount.getEmail());
        }
        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alert.cancel();


            }
        });
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(AdmiMainActivity.this, LoginActivity.class));
                finish();

            }


        });

        alert.setView(view);

        alert.show();




    }

    private void getUserinfo() {
        String id = mAuth.getCurrentUser().getUid();
        mDatabase.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {
                    Name_DB = dataSnapshot.child("name").getValue().toString();
                    Email_DB = dataSnapshot.child("email").getValue().toString();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) return false;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Network networks = connectivityManager.getActiveNetwork();
            if (networks == null) return false;

            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(networks);
            if (networkCapabilities == null) return false;

            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
                return true;
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
                return true;
            return false;

        } else {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            if (activeNetworkInfo == null) return false;

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) return true;
            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_ETHERNET) return true;
            return false;

        }
    }


    public void agregar(View view) {
        Intent intent3 = new Intent(AdmiMainActivity.this, AgregarActivity.class);

        startActivity(intent3);


    }


}