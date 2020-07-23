package com.gilat.proyectogilat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.gilat.proyectogilat.Entidades.ConfAntena;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MapaUserActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    String flag = "";
    String la;
    String lo;
    String var;
    String ubi;
    LatLng ubicacion1;
    List<ConfAntena> lista = new ArrayList<>();
    NavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa_user);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            flag = extras.getString("flag");

        }


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

                        break;
                    case R.id.nav_lista:
                        Intent i = new Intent(MapaUserActivity.this, MainActivity.class);
                        i.putExtra("flag",flag);
                        startActivity(i);

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
        navigationView.setCheckedItem(R.id.nav_mapa);

        //----------------------------------------------------------------------------------------------------------------------



        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);




    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {
                // Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            //Log.e(TAG, "Can't find style. Error: ", e);
        }




        readData(new MainActivity.MyCallback() {
            @Override
            public void onCallback(List<ConfAntena> list) {
                Log.d("TAG", String.valueOf(list.size()));

                for (ConfAntena ga : list) {
                    ubicacion1 = new LatLng(ga.getLatitud(), ga.getLongitud());
                    la = String.valueOf(ga.getLatitud());
                    lo = String.valueOf(ga.getLongitud());
                    var = la + " , " + lo;
                    ubi = ga.getNombre();
                    mMap.addMarker(new MarkerOptions().position(ubicacion1).title(ubi).snippet(var).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

                }
            }
        });

        LatLng ubicacion2 = new LatLng(-11.910905985970672, -77.05563256573937);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion2, 13));
        mMap.getUiSettings().setZoomControlsEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);




    }


    public interface MyCallback {
        void onCallback(List<ConfAntena> list);
    }
    private void readData(final MainActivity.MyCallback myCallback){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("ConfAntena").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //List<ConfAntena> lista = new ArrayList<>();

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



}