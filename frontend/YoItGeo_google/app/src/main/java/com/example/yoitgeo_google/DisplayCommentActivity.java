package com.example.yoitgeo_google;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.view.ViewGroup;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DisplayCommentActivity extends AppCompatActivity {
    private static TextView title;
    private static TextView geo_exp;
    private static TextView gps_name;
    private Button button;
    ListView list;
    String[][] igidaeComment = {{"이기대", "바다를 향한 불의 신(VULCAN)\n약 8천만 년 전 격렬했던 안산암질 화산활동으로 분출된 용암과 화산재, 화쇄류가 쌓여 만들어진 다양한 화산암 및 퇴적암 지층들이 파도의 침식으로 발달된 해식애, 파식대지, 해식동굴과 함께 천혜의 절경을 이루고 있습니다.\n해안가를 따라 오륙도까지 이어지는 트레일코스를 통해 구리광산, 돌개구멍, 말꼬리구조, 함각섬석 암맥 등의 다양한 지질 및 지형경관을 감상할 수 있습니다."}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_comment);

        TextView textViewTitle = (TextView) findViewById(R.id.title);
        TextView textViewComment = (TextView) findViewById(R.id.comment);

        textViewTitle.setText(igidaeComment[0][0]);
        textViewComment.setText(igidaeComment[0][1]);
        list = (ListView) findViewById(R.id.listView);
        geo_exp = (TextView) findViewById(R.id.geo_exp);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                  sendRequest();
            }
        });

        if (AppHelper.requestQueue == null) {
            //리퀘스트 생성(MainActivity가 메모리에서 만들어질 때 같이 생성될 것이다.)
            AppHelper.requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
    }

    public static void sendRequest() {
        String url = "http://192.168.219.100:3000/gps_site";

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
                        println(error.getMessage());
                    }
                }
        ){
            /*
            * 만약 POST 방식에서 전달할 요청 파라미터가 있다면 getParams 메소드에서 반환하는 HashMap 객체에 넣어줍니다.
            * 이렇게 만든 요청 객체는 요청 큐에 넣어주는 것만 해주면 됩니다.
            * POST 방식으로 안할거면 없어도 되는 것 같다.
            * */
            @Override
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                return params;
            }
        };

        request.setShouldCache(false);
        AppHelper.requestQueue.add(request);
        println("요청보냄!");
    }

    public static void println(String data) {
        geo_exp.append(data + "\n");
    }

}