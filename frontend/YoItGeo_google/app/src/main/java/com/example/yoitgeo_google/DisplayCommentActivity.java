package com.example.yoitgeo_google;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
    ListView gps_name_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);

        geo_name = (TextView) findViewById(R.id.geo_name);
        geo_exp = (TextView) findViewById(R.id.geo_exp);

        geo_name.setText(dbServer.getGeoname());
        geo_exp.setText(dbServer.getGeo_exp());

        //gps_site 정보 불러오기
        gps_name_list = (ListView) findViewById(R.id.view_gps_name);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,dbServer.gps_name_list);
        gps_name_list.setAdapter(adapter);

        gps_name_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),DisplaySubCommentActivity.class);
                //dbServer.setChosen_gps_name(dbServer.gps_name_list.get(position));
                intent.putExtra("gps_name",dbServer.gps_name_list.get(position));
                intent.putExtra("gps_exp",dbServer.gps_exp_list.get(position));
                //Log.d("gps_name",dbServer.gps_name_list.get(position));
                Log.d("gps_exp",dbServer.gps_exp_list.get(position));
                startActivity(intent);
            }
        });


    }
}
