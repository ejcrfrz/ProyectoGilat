package com.gilat.proyectogilat;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity1 extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String nombre_incidencia = "";
    String flag = "";
    String pola="";
    String frecuencia ="";
    double latitud =0.0;
    double longitud=0.0;
    String potx="";
    String potrx="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps1);

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

        }



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




        LatLng ubicacion1 = new LatLng(latitud,longitud);
        String la = String.valueOf(latitud);
        String lo = String.valueOf(longitud);
        String var = la +" , "+lo;
        String ubi = nombre_incidencia;
        mMap.addMarker(new MarkerOptions().position(ubicacion1).title(ubi).snippet(var).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));


        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion1, 13));
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

    public void volver(View view){
        Intent intent1 =new Intent(MapsActivity1.this,InfoActivity.class);
        intent1.putExtra("flag",flag);
        intent1.putExtra("nombre_incidencia",nombre_incidencia);
        intent1.putExtra("pola",pola);
        intent1.putExtra("frecuencia",frecuencia);
        intent1.putExtra("latitud",latitud);
        intent1.putExtra("longitud",longitud);
        intent1.putExtra("potx",potx);
        intent1.putExtra("potrx",potrx);

        startActivity(intent1);

        finish();


    }
}