package com.example.yoitgeo_google;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        // 로딩페이지(스플래쉬)
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
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
}
