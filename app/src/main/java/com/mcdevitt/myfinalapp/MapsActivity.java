package com.mcdevitt.myfinalapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        GoogleMap map = mapFragment.getMap();
        map.setMyLocationEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

    }

    public void search(View v) {

        EditText name = (EditText) findViewById(R.id.countryTextView);
        String location = name.getText().toString();

        try {
            Geocoder gc = new Geocoder(this);
            List<Address> list = gc.getFromLocationName(location, 1);

            if (list.size() != 0) {

                Address add = list.get(0);
                double lat = add.getLatitude();
                double lng = add.getLongitude();
                LatLng ll = new LatLng(lat, lng);
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                GoogleMap map = mapFragment.getMap();
                map.moveCamera(CameraUpdateFactory.newLatLng(ll));
                // zoom in Google map
                map.animateCamera(CameraUpdateFactory.zoomTo(6));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng random = new LatLng(53.338340, -6.266193);
        map.addMarker(new MarkerOptions().position(random).title("Intreo Centre Bishop's Square"));
        map.moveCamera(CameraUpdateFactory.newLatLng(random));


    }
}