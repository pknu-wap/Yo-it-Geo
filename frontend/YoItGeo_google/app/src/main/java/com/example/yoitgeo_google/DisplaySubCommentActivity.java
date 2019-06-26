package com.example.yoitgeo_google;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplaySubCommentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sub_comment);

        String title = "";
        int id;

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            title = "error";
        }
        else {
            title = extras.getString("title");
            id = extras.getInt("id");
        }

        TextView textView = (TextView) findViewById(R.id.textView_contentString);

        String str = title + '\n';
        textView.setText(str);

        /*
        id : 지명
        1 : 화산각력암층
        2 : 함각섬석 암맥
        3 : 구리광산
        4 : 치마바위
        5 : 밭골새
        6 : 농바위
         */
    }
}
