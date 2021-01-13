package com.example.yoitgeo_google;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class dbServer {
    public static String firstURL = "http://192.168.219.100:3000/";

    public static String getGeoname() {
        return geoname;
    }

    public static void setGeoname(String geoname) {
        dbServer.geoname = geoname;
    }

    private static String geoname;

    public static String getGeo_exp() {
        return geo_exp;
    }

    public static void setGeo_exp(String geo_exp) {
        dbServer.geo_exp = geo_exp;
    }

    private static String geo_exp;

    public static ArrayList<String> gps_name_list;  //지역에 따른 지점명소 이름 리스트(설명 밑에 보여주기 위해 사용)

}