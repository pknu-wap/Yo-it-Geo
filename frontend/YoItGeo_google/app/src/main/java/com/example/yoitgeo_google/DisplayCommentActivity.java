package com.example.yoitgeo_google;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.TextView;

public class DisplayCommentActivity extends AppCompatActivity {
    String[][] igidaeComment = {{"이기대", "바다를 향한 불의 신(VULCAN)\n약 8천만 년 전 격렬했던 안산암질 화산활동으로 분출된 용암과 화산재, 화쇄류가 쌓여 만들어진 다양한 화산암 및 퇴적암 지층들이 파도의 침식으로 발달된 해식애, 파식대지, 해식동굴과 함께 천혜의 절경을 이루고 있습니다.\n해안가를 따라 오륙도까지 이어지는 트레일코스를 통해 구리광산, 돌개구멍, 말꼬리구조, 함각섬석 암맥 등의 다양한 지질 및 지형경관을 감상할 수 있습니다."}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);

        TextView textViewTitle = (TextView) findViewById(R.id.title);
        TextView textViewComment = (TextView) findViewById(R.id.comment);

        textViewTitle.setText(igidaeComment[0][0]);
        textViewComment.setText(igidaeComment[0][1]);
    }
}
