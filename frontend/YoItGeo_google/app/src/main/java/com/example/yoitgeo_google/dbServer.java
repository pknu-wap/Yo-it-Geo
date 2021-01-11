package com.example.yoitgeo_google;

public class dbServer {
    public static String firstURL = "http://192.168.219.100:3000/";

    public static String getGeoname() {
        return geoname;
    }

    public static void setGeoname(String geoname) {
        dbServer.geoname = geoname;
    }

    public static String geoname;


}
