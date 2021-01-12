package com.example.yoitgeo_google;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {
    Button btn;
    ArrayList<String> test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        // 로딩페이지(스플래쉬)
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
        final Intent mainIntent = new Intent(this,MainActivity.class);
        test = extractNameList();


        btn = (Button)findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                //Log.d("geo_name:",test.get(1));
                dbServer.setGeoname(test.get(1));  //"geo_name 변수 넘기기: 해설, 체험 프로그램 때 사용"
                //Log.d("dbServer.geoname",dbServer.getGeoname());
                startActivity(mainIntent);
            }
        });



    }

    public void choosePark(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        switch (view.getId()) {
            case R.id.button1:
                startActivity(intent);
                break;
            default:
                Toast.makeText(getApplicationContext(), "구현 중입니다", Toast.LENGTH_LONG).show();
                break;
        }
    }

    //지리 이름 목록 arrayList으로 저장
    public ArrayList<String> extractNameList(){
        final ArrayList<String> nameList = new ArrayList<String>();
        dbServer.nameList=new ArrayList<>();
        String url = dbServer.firstURL+"geosite";

        StringRequest obreq = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject jsonObj = new JSONObject(response);
                            //Log.d("test",response);
                            JSONArray array = jsonObj.getJSONArray("data");

                            for(int i=0;i<array.length();i++){
                                JSONObject obj = array.getJSONObject(i);
                                //Log.d("name", obj.getString("geo_name"));
                                nameList.add(obj.getString("geo_name"));
                                dbServer.nameList.add(obj.getString("geo_name"));
                                Log.d("dbServer.nameList",dbServer.nameList.get(i));
                                //Log.d("nameList",nameList.get(i));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley","Error");
                    }
                }
        );
        obreq.setShouldCache(false);//이전 결과가 있어도 새로 요청하여 응답을 보여준다.
        AppHelper.requestQueue = Volley.newRequestQueue(this); // requestQueue 초기화 필수
        AppHelper.requestQueue.add(obreq);

        return (nameList);
    }

}
