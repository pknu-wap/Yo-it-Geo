package com.example.yoitgeo_google;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        LatLng IGIDAE = new LatLng(35.132242, 129.120661);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(IGIDAE);
        markerOptions.title("이기대");
        markerOptions.snippet("코스 시작");
        map.addMarker(markerOptions);

        map.moveCamera(CameraUpdateFactory.newLatLng(IGIDAE));
        map.animateCamera(CameraUpdateFactory.zoomTo(10));
    }

}
