package com.example.certamen1app;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener {

    private GoogleMap mMap;
    private MarkerOptions marker;
    private static final int LOCATION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        findViewById(R.id.btnGps).setOnClickListener(this);
        findViewById(R.id.btnSatelite).setOnClickListener(this);
        findViewById(R.id.btnNormal).setOnClickListener(this);



    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (!this.checkGPS()){
            alertaGPS();
        }else{
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
             mMap.setMyLocationEnabled(true);
            } else {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Mostrar diálogo explicativo
        } else {
            // Solicitar permiso
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_REQUEST_CODE);
        }
    }
}
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);

        mMap.setMinZoomPreference(5);
        //mMap.setMaxZoomPreference(15);

    }

    private boolean checkGPS(){
        try {

            int gpsSignal = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            if (gpsSignal != 0){
                return true;
            }else{
                return false;
            }

        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void alertaGPS(){
        new AlertDialog.Builder(MapsActivity.this)
                .setTitle("Alerta GPS")
                .setMessage("El GPS se encuentra desactivado, ¿Desea activarlo?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intentgps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(intentgps);
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btnGps){
            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }else if(i == R.id.btnSatelite){
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else if(i == R.id.btnNormal){
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
    }
}
