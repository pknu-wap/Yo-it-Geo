package com.example.yoitgeo_google;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DisplaySubCommentActivity extends AppCompatActivity {
    private TextView title;
    private TextView explanation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sub_comment);
        title = (TextView) findViewById(R.id.gps_title);
        explanation= (TextView)findViewById(R.id.gps_exp);

        Intent intent = getIntent();    //데이터 수신
        String gps_title = intent.getExtras().getString("gps_name");
        String gps_exp = getIntent().getStringExtra("gps_exp");
       Log.d("geo_title",gps_exp);

        title.setText(gps_title);
        explanation.setText(gps_exp);
    }
}
