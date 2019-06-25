package com.example.yoitgeo_google;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;



public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnMarkerClickListener {

    private GoogleMap mGoogleMap = null;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용
    private static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // GoogleApiClient의 인스턴스 생성, 지우지 말것!, 현재 위치정보 얻을 때 필요
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            Toast.makeText(this, "현재 위치 표시를 위해 GPS 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 승인될 경우
                    System.out.println("권한 승인됨");
                } else {
                    Toast.makeText(this, "권한 체크 거부 됨", Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        //지도의 초기위치를 이기대로 이동
        setDefaultLocation();



        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));



        LatLng IGIDAE = new LatLng(35.132242, 129.120661);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(IGIDAE);
        markerOptions.title("이기대");
        markerOptions.snippet("코스 시작");

        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.green_leaf_marker);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));

        //googleMap.addMarker(markerOptions);

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(IGIDAE));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));



        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1
                .position(new LatLng(35.128082, 129.122373))
                .title("화산각력암층");
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions1);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2
                .position(new LatLng(35.127511, 129.122423))
                .title("함각섬석 암맥");
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3
                .position(new LatLng(35.125402, 129.123154))
                .title("구리광산");
        markerOptions3.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions3);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4
                .position(new LatLng(35.114814, 129.128166))
                .title("치마바위");
        markerOptions4.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions4);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5
                .position(new LatLng(35.112427, 129.127479))
                .title("밭골새");
        markerOptions5.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions5);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6
                .position(new LatLng(35.109549, 129.127050))
                .title("농바위");
        markerOptions6.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions6);


        googleMap.setOnMarkerClickListener(this);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle() + "해설 페이지로 이동합니다", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this, DisplaySubCommentActivity.class);
        startActivity(intent);

        // 팝업창 만들기

        return true;
    }



    @Override
    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }
    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }



    public void setDefaultLocation() {
        //디폴트 위치, 이기대 출발점
        LatLng DEFAULT_LOCATION = new LatLng(35.132242, 129.120661);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);
    }



    //GPS 활성화를 위한 메소드
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) { }
    @Override
    public void onConnectionSuspended(int i) { }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { }



    // 버튼 이벤트
    public void igidaeComment(View view) {
        Intent intent = new Intent(this, DisplayCommentActivity.class);
        startActivity(intent);
    }

    public void igidaeReservation(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.busan.go.kr/geopark/tm0303"));
        startActivity(intent);
        finish();
    }

    public void igidaeGame(View view) {
        Intent intent = new Intent(this, DisplayGameActivity.class);
        startActivity(intent);
    }

    public void onLastLocationButtonClicked(View view) {
        // 권한 체크
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
                return;
        }
        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    // 현재 위치
                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
                    mGoogleMap.addMarker(new MarkerOptions()
                            .position(myLocation)
                            .title("현재 위치"));
                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
                    // 카메라 줌
                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
                }
            }
        });
    }
}
