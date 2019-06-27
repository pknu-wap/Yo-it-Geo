package com.example.yoitgeo_google;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;

import java.util.Random;

public class DisplayGameActivity extends AppCompatActivity implements View.OnClickListener {
    Button btn, btn2;
    TextView tv_question;
    private String answer;
    private int questionLength=5;
    Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_game);

        random = new Random();

        btn=(Button)findViewById(R.id.btn);
        btn.setOnClickListener(this);
        btn2=(Button)findViewById(R.id.btn2);
        btn2.setOnClickListener(this);

        tv_question=(TextView)findViewById(R.id.tv_question);
        NextQuestion(random.nextInt(questionLength));
    }

    public void GameOver(){
        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage("게임 종료")
                .setCancelable(false)
                .setPositiveButton("새 게임", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int whichbutton){
                        startActivity(new Intent(getApplicationContext(), DisplayGameActivity.class));
                    }
                })
                .setNegativeButton("나가기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichbutton) {
                        System.exit(0);
                    }
                });
        alertDialogBuilder.show();

    }
    public void NextQuestion(int num){
        tv_question.setText(getQuestion(num));
        btn.setText(getchoice1(num));
        btn2.setText(getchoice2(num));

        answer = getCorrAnswer(num);
    }

    //@Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btn:
                if(btn.getText()==answer){
                    Toast.makeText(this,"정답입니다.", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
                break;

            case R.id.btn2:
                if(btn2.getText()==answer){
                    Toast.makeText(this, "정답입니다.", Toast.LENGTH_SHORT).show();
                    NextQuestion(random.nextInt(questionLength));
                }else{
                    GameOver();
                }
                break;
        }



    }

    public String questions[]={"이기대에는 공룡발자국화석이 존재한다.","이기대에는 'ㄱ'자형으로 붉은색 페인트가 칠해진 바위가 존재한다.",
            "과거 이기대의 지각은 바다에 잠겨 있었다.", "이기대에의 해녀 막사는 해녀들이 \n바다에서 잡아온 어구를 파는 곳이다.",
            "이기대는 과거 안산암질 마그마 분출에 의해 생성된 \n다양한 화산암 및 퇴적암 지층으로 이루어져 있다."};
    public String answers[][]={
            {"O","X"},{"O","X"},{"O","X"},{"O","X"},{"O","X"}
    };
    public String corrAnswer[]={"X", "X", "O", "X", "O"};

    public String getQuestion(int a){
        String question=questions[a];
        return question;
    }

    public String getchoice1(int a){
        String choice=answers[a][0];
        return choice;
    }
    public String getchoice2(int a){
        String choice=answers[a][1];
        return choice;
    }
    public String getchoice3(int a){
        String choice=answers[a][2];
        return choice;
    }
    public String getchoice4(int a){
        String choice=answers[a][3];
        return choice;
    }
    public String getchoice5(int a){
        String choice=answers[a][4];
        return choice;
    }
    public String getCorrAnswer(int a){
        String answer=corrAnswer[a];
        return answer;
    }
}
