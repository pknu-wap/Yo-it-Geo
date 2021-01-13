package com.example.yoitgeo_google;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DisplayCommentActivity extends AppCompatActivity {
    TextView geo_name;
    TextView geo_exp;
    Button button;
    String url;
    //String[] siteName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);

        geo_name = (TextView) findViewById(R.id.geo_name);
        geo_exp = (TextView) findViewById(R.id.geo_exp);
        button = (Button) findViewById(R.id.button);

        geo_name.setText(dbServer.getGeoname());
        geo_exp.setText(dbServer.getGeo_exp());
        // sendRequest("geosite");

        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                sendRequest("geosite");
            }
        });
        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }


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
                                Log.d("name", obj.getString("geo_name"));
                                Log.d("exp", obj.getString("geo_explanation"));
                                if (dbServer.getGeoname().equals(obj.getString("geo_name"))) {
                                    //if (obj.getString("geo_name").equals(dbServer.nameList.get(j))) {
                                    // Log.d("geo", obj.getString("geo_explanation"));

                                    geo_exp.setText(obj.getString("geo_explanation"));
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

 */

    }
/*
    public void println(String data){
        geo_exp.append(data+"\n");
    }
 */
}
