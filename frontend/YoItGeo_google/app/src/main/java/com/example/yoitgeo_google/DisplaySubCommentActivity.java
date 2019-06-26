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
        //String snippet = "";

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            title = "error";
        }
        else {
            title = extras.getString("title");
            //snippet = extras.getString("snippet");
        }

        TextView textView = (TextView) findViewById(R.id.textView_contentString);

        String str = title + '\n';
        textView.setText(str);
    }
}
