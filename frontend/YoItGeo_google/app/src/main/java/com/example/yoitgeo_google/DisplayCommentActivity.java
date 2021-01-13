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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);

        geo_name = (TextView) findViewById(R.id.geo_name);
        geo_exp = (TextView) findViewById(R.id.geo_exp);

        geo_name.setText(dbServer.getGeoname());
        geo_exp.setText(dbServer.getGeo_exp());

    }
}
