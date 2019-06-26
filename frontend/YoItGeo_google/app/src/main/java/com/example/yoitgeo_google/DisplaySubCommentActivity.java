package com.example.yoitgeo_google;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DisplaySubCommentActivity extends AppCompatActivity {
    String[][] igidaeComment = {{"지형", "설명"},
            {"돌개구멍", "해안가에 발달한 화산각력암에 마치 공룡 발자국과 같은 형태가 보입니다. 이는 바위의 빈틈에 들어간 자갈이나 모래가 파도에 의해 회전하면서 서서히 바위를 깎아내어 만들어진 돌개구멍(포트홀, phthole)입니다."},
            {"구리광산", "일제강점기때 이 일대에 총 5개의 구리광산 갱도가 있었는데, 그 중 하나였던 이곳은 깊이가 무려 수평 550m, 수직 380m에 달했다고 합니다. 당시 이곳에서는 순도 99.9%의 황동이 많이 생산되었으나, 지금은 갱도입구가 막혀있습니다."},
            {"해식동굴", "가파른 절벽을 이루는 암석의 약한 부분이나 빈틈이 오랜 시간동안 파도에 의해 깎여 나가 만들어진 자연동굴입니다. 이기대의 해식동굴은 육지에 노출되어 있어 관찰하고 체험할 수 있습니다."},
            {"함각섬석암맥", "뜨거운 마그마가 지층의 약한 부분이나 빈틈을 맥상으로 뚫고 올라간 암맥을 볼 수 있습니다. 이곳의 암맥은 유색광물 중의 하나인 각섬석이 큰 결정으로 다량 함유되어 있는 것을 관찰할 수 있습니다."},
            {"화산각력암층", "백악기 당시 폭발적인 화산 분출로 인해 쌓인 안산암질 화산각력암이 트레일을 따라 넓게 발달되어 있습니다."},
            {"보석광물벽옥", "화산각력암에 마치 붉은색 물감으로 칠을 해놓은 듯한 이것은 보석광물인 벽옥(jasper)입니다. 보통 화산 분출로 형성된 화산암이나 응회암 중에서 맥을 이루며 산출됩니다."},
            {"응회질퇴적암층", "폭발적인 화산활동 이후 휴지기에 화산재들이 섞여 있는 퇴적암(사암과 이암)이 층층이 쌓여 만든 층리가 잘 나타납니다."}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_sub_comment);

        int id = 0;

        Bundle extras = getIntent().getExtras();

        if (extras == null) {
            System.out.println("Error");
        }
        else {
            id = extras.getInt("id");
        }

        TextView textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        TextView textViewComment = (TextView) findViewById(R.id.textViewComment);

        String title = igidaeComment[id][0];
        String comment = igidaeComment[id][1];
        textViewTitle.setText(title);
        textViewComment.setText(comment);
    }
}
