package com.example.asus.songrecognition.model;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpConnection {


    public String getJsonString(String url, String method, ArrayList<SongFingerprints> params) {
        String result = "";
        HttpURLConnection conn;
        URL urlObj;

        try {
            urlObj = new URL(url);
            conn = (HttpURLConnection) urlObj.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            //conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);

            if (method.equalsIgnoreCase("GET")){
                conn.setRequestMethod("GET");
                conn.connect();
            }

            else if (method.equalsIgnoreCase("POST")){
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setRequestProperty("Accept", "application/json");
                conn.connect();
                OutputStream ostream = conn.getOutputStream();
                ostream.write(getQuery(params).getBytes("UTF-8"));
                ostream.close();

            }

            int responseCode=conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                while ((line = br.readLine()) != null)
                {
                    result = result + line;
                }
            }
            else
                result="Koneksi Gagal";

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }

    public JsonArray getJsonArray(String url, String method, ArrayList<SongFingerprints> params) {
        try{
            String jsonString = getJsonString(url,method,params);
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonString);
            JsonArray jsonArray = jsonObject.getAsJsonArray("SongMetadata");
            return jsonArray;
        }catch (ClassCastException e){
            return null;
        }
    }

    public JsonObject getJsonObject(String url, String method, ArrayList<SongFingerprints> params){
        try{
            String jsonString = getJsonString(url,method,params);
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(jsonString);
            return jsonObject;
        }catch (ClassCastException e){
            return null;
        }
    }

    private String getQuery(ArrayList<SongFingerprints>  params) {
        Gson gson = new Gson();
        JsonArray jsonArray = gson.toJsonTree(params).getAsJsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("fingerprint",jsonArray);
        String jsonString = jsonObject.toString();

        return jsonString;
    }
}

