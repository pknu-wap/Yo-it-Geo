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
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsRequest;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mGoogleMap = null;
    private GoogleApiClient mGoogleApiClient;
    private FusedLocationProviderClient mFusedLocationClient;

    // onRequestPermissionsResult에서 수신된 결과에서 ActivityCompat.requestPermissions를 사용한 퍼미션 요청을 구별하기 위해 사용
    private static final int PERMISSIONS_REQUEST_CODE = 1000;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int UPDATE_INTERVAL_MS = 1000;
    private static final int FASTEST_UPDATE_INTERVAL_MS = 500;


    private Marker currentMarker = null;
    Location mCurrentLocation;
    LatLng currentPosition;
    private LocationRequest locationRequest;
    private Location location;

    private Button comment;
    private String url;

    MarkerOptions[] arrMarkerOptions = new MarkerOptions[9];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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


        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            Toast.makeText(this, "현재 위치 표시를 위해 GPS 권한이 필요합니다.", Toast.LENGTH_LONG).show();
            showDialogForLocationServiceSetting();
        }


        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL_MS)
                .setFastestInterval(FASTEST_UPDATE_INTERVAL_MS);
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);

        //해설 넘어갈 때 geo_explanation 불러오기
        comment = (Button)findViewById(R.id.button_comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequest("geosite");
                sendRequest("gps_site");
                Intent intent = new Intent(getApplicationContext(), DisplayCommentActivity.class);
                startActivity(intent);
            }
        });

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }

    }


    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);

            List<Location> locationList = locationResult.getLocations();

            if (locationList.size() > 0) {
                location = locationList.get(locationList.size() - 1);
                //location = locationList.get(0);

                currentPosition = new LatLng(location.getLatitude(), location.getLongitude());

//                String markerTitle = "현재 위치";
//                String markerSnippet = "위도:" + String.valueOf(location.getLatitude()) + " 경도:" + String.valueOf(location.getLongitude());

                // 현재 위치에 마커 생성하고 이동
                //setCurrentLocation(location, markerTitle, markerSnippet);
                int result = checkDistance(location.getLatitude(), location.getLongitude());
                if (result != -1) {
                    setQuiz(arrMarkerOptions[result].getTitle());
                    mFusedLocationClient.removeLocationUpdates(locationCallback);
                }
                if (result == -1 && checkPermission()) {
                    mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                }

                mCurrentLocation = location;
            }
        }
    };


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            return;
        }
        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

        if (checkPermission())
            mGoogleMap.setMyLocationEnabled(true);
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



        LatLng IGIDAE = new LatLng(35.126493, 129.122918);

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



        MarkerOptions markerOptions0 = new MarkerOptions();
        markerOptions0
                .position(new LatLng(35.134818, 129.102960))
                .title("0")
                .snippet("누리관");

        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1
                .position(new LatLng(35.123654, 129.123858))
                .title("1")
                .snippet("돌개구멍");
        markerOptions1.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions1);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2
                .position(new LatLng(35.125402, 129.123154))
                .title("2")
                .snippet("구리광산");
        markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions2);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3
                .position(new LatLng(35.127080, 129.122297))
                .title("3")
                .snippet("해식동굴");
        markerOptions3.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions3);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4
                .position(new LatLng(35.127511, 129.122423))
                .title("4")
                .snippet("함각섬석암맥");
        markerOptions4.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions4);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5
                .position(new LatLng(35.128082, 129.122373))
                .title("5")
                .snippet("화산각력암층");
        markerOptions5.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions5);

        MarkerOptions markerOptions6 = new MarkerOptions();
        markerOptions6
                .position(new LatLng(35.128574, 129.121985))
                .title("6")
                .snippet("보석광물벽옥");
        markerOptions6.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions6);

        MarkerOptions markerOptions7 = new MarkerOptions();
        markerOptions7
                .position(new LatLng(35.129332, 129.121977))
                .title("7")
                .snippet("응회질퇴적암층");
        markerOptions7.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
        googleMap.addMarker(markerOptions7);

        MarkerOptions markerOptions8 = new MarkerOptions();
        markerOptions8
                .position(new LatLng(35.129332, 129.121977))
                .title("8")
                .snippet("향파관");

        MarkerOptions[] arrMO = {markerOptions0, markerOptions1, markerOptions2, markerOptions3,
                                        markerOptions4, markerOptions5, markerOptions6, markerOptions7, markerOptions8};
        System.arraycopy(arrMO, 0, arrMarkerOptions, 0, arrMO.length);


        mGoogleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                accessMarker(marker.getTitle());
            }
        });


        startLocationUpdates();
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

            }
        });
    }

//    @Override
//    public boolean onMarkerClick(Marker marker) {
//        accessMarker(marker.getTitle());
//
//        return true;
//    }



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


//    public void setCurrentLocation(Location location, String markerTitle, String markerSnippet) {
//        if (currentMarker != null) currentMarker.remove();
//
//        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
//
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(currentLatLng);
//        markerOptions.title(markerTitle);
//        markerOptions.snippet(markerSnippet);
//        markerOptions.draggable(true);
//
//        BitmapDrawable bitmapdraw = (BitmapDrawable)getResources().getDrawable(R.drawable.red_leaf_marker);
//        Bitmap b = bitmapdraw.getBitmap();
//        Bitmap smallMarker = Bitmap.createScaledBitmap(b, 100, 100, false);
//        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
//        currentMarker = mGoogleMap.addMarker(markerOptions);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(currentLatLng);
//        mGoogleMap.moveCamera(cameraUpdate);
//
//
//
//        int result = checkDistance(currentMarker.getPosition().latitude, currentMarker.getPosition().longitude);
//        if (result != -1) {
//            accessMarker(arrMarkerOptions[result].getTitle());
//            mFusedLocationClient.removeLocationUpdates(locationCallback);
//        }
//        if (result == -1 && checkPermission()) {
//                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
//        }
//    }



    public void setDefaultLocation() {
        //디폴트 위치, 이기대 출발점
        LatLng DEFAULT_LOCATION = new LatLng(35.132242, 129.120661);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, 15);
        mGoogleMap.moveCamera(cameraUpdate);
    }


    private boolean checkPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
            return false;
        }
        return true;
    }



    //GPS 활성화
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

    public void igidaeReservation(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.busan.go.kr/geopark/tm0303"));
        startActivity(intent);
        finish();
    }

    public void igidaeGame(View view) {
        Intent intent = new Intent(this, DisplayGameActivity.class);
        startActivity(intent);
    }

//    public void onLastLocationButtonClicked(View view) {
//        // 권한 체크
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSIONS_REQUEST_CODE);
//                return;
//        }
//        mFusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    // 현재 위치
//                    LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());
//                    mGoogleMap.addMarker(new MarkerOptions()
//                            .position(myLocation)
//                            .title("현재 위치"));
//                    mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
//                    // 카메라 줌
//                    mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(17.0f));
//                    System.out.println(myLocation.latitude + ", " + myLocation.longitude);
//                }
//            }
//        });
//
//    }

    public double getDistance(double latitude1, double longitude1, double latitude2, double longitude2) {
        Location startPos = new Location("PointA");
        Location endPos = new Location("PointB");

        startPos.setLatitude(latitude1);
        startPos.setLongitude(longitude1);
        endPos.setLatitude(latitude2);
        endPos.setLongitude(longitude2);


        return startPos.distanceTo(endPos); // 미터
    }

    public int checkDistance(double latitude, double longitude) {
        for (int i=0; i<arrMarkerOptions.length; i++) {
            if (getDistance(latitude, longitude, arrMarkerOptions[i].getPosition().latitude, arrMarkerOptions[i].getPosition().longitude) <= 50) {
                return i;
            }
        }
        return -1;
    }

    public void setDialog(String s) {
        final int id = Integer.parseInt(s);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(arrMarkerOptions[id].getSnippet() + "에 대한 설명을 보시겠습니까?");
        builder.setPositiveButton("예",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), arrMarkerOptions[id].getSnippet() + " 설명 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), DisplaySubCommentActivity.class);

                        intent.putExtra("id", id);

                        startActivity(intent);
                    }
                });
        builder.setNegativeButton("아니오",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "아니오를 선택했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void setQuiz(String s) {
        final String str = s;
        final int id = Integer.parseInt(s);

        final String[][] quiz = {{"ㄴㄹㄱ", "누리관"},
                {"ㄷㄱㄱㅁ", "돌개구멍"},
                {"ㄱㄹㄱㅅ", "구리광산"},
                {"ㅎㅅㄷㄱ", "해식동굴"},
                {"ㅎㄱㅅㅅㅇㅁ", "함각섬석암맥"},
                {"ㅎㅅㄱㄹㅇ", "화산각력암"},
                {"ㅂㅇ", "벽옥"},
                {"ㅇㅎㅈㅌㅈㅇㅊ", "응회질퇴적암층"},
                {"ㅎㅍㄱ", "향파관"}};

        final EditText editText = new EditText(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("초성 퀴즈");
        builder.setMessage("현재 이 곳의 명칭은?\n" + "힌트 : " + quiz[id][0]);
        builder.setView(editText);
        builder.setPositiveButton("입력",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String answer = editText.getText().toString();
                        if (answer.equals(quiz[id][1])) {
                            Toast.makeText(getApplicationContext(), "맞았습니다", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "틀렸습니다", Toast.LENGTH_SHORT).show();

                            setDialog(str);
                        }
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        //Toast.makeText(getApplicationContext(), "취소를 선택했습니다.", Toast.LENGTH_LONG).show();
                    }
                });
        builder.show();
    }

    public void accessMarker(String s) {
        final int id = Integer.parseInt(s);

        Toast.makeText(getApplicationContext(), arrMarkerOptions[id].getSnippet() + " 설명 페이지로 이동합니다.", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getBaseContext(), DisplaySubCommentActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }

    public void sendRequest(String name) {

        url = dbServer.firstURL + name;

        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            //Log.d("test",response);
                            JSONArray array = jsonObj.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                // Log.d("name", obj.getString("geo_name"));
                                //Log.d("exp", obj.getString("geo_explanation"));

                                if (dbServer.getGeoname().equals(obj.getString("geo_name"))) {
                                    dbServer.setGeo_exp(obj.getString("geo_explanation"));
                                }
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        obreq.setShouldCache(false);//이전 결과가 있어도 새로 요청하여 응답을 보여준다.
        AppHelper.requestQueue = Volley.newRequestQueue(this); // requestQueue 초기화 필수
        AppHelper.requestQueue.add(obreq);
    }
    public void sendRequestGPS(String name) {
        url = dbServer.firstURL + name;

        StringRequest obreq = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            //Log.d("test",response);
                            JSONArray array = jsonObj.getJSONArray("data");

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject obj = array.getJSONObject(i);
                                dbServer.gps_name_list.add(obj.getString("gps_name"));
                                Log.d("gps_name_list element",dbServer.gps_name_list.get(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );
        obreq.setShouldCache(false);//이전 결과가 있어도 새로 요청하여 응답을 보여준다.
        AppHelper.requestQueue = Volley.newRequestQueue(this); // requestQueue 초기화 필수
        AppHelper.requestQueue.add(obreq);
    }
/*
        StringRequest request = new StringRequest(Request.Method.GET,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        println(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        println("에러다 =>" + error.getMessage());
                    }
                }){
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                return params;
            }
        };
}
 */

/*
    public void println(String data){
        geo_exp.append(data+"\n");
    }
 */





///////////////////////게임하기
//    public void GameOver(){
//        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
//        alertDialogBuilder
//                .setMessage("게임 종료")
//                .setCancelable(false)
//                .setPositiveButton("새 게임", new DialogInterface.OnClickListener(){
//                    @Override
//                    public void onClick(DialogInterface dialog, int whichbutton){
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    }
//                })
//                .setNegativeButton("나가기", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int whichbutton) {
//                        System.exit(0);
//                    }
//                });
//        alertDialogBuilder.show();
//
//    }
//    public void NextQuestion(int num){
//        tv_question.setText(question.getQuestion(num));
//        btn.setText(question.getchoice1(num));
//        btn2.setText(question.getchoice2(num));
//
//        answer = question.getCorrAnswer(num);
//    }
//
//    //@Override
//    public void onClick(View v){
//        switch(v.getId()){
//            case R.id.btn:
//                if(btn.getText()==answer){
//                    Toast.makeText(this,"정답입니다.", Toast.LENGTH_SHORT).show();
//                    NextQuestion(random.nextInt(questionLength));
//                }else{
//                    GameOver();
//                }
//                break;
//
//            case R.id.btn2:
//                if(btn2.getText()==answer){
//                    Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show();
//                    NextQuestion(random.nextInt(questionLength));
//                }else{
//                    GameOver();
//                }
//                break;
//        }
//
//
//
//    }

}
