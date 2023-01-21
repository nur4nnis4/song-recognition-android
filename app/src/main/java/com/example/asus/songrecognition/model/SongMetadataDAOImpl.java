package com.example.asus.songrecognition.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class SongMetadataDAOImpl implements SongMetadataDAO{
    private static final String SERVLET_URL = "http://localhost:8080/SongRecognitionServer/Servlet";


    @Override
    public SongMetadata SearchMetaData(ArrayList<SongFingerprints> fingerprints) {
        SongMetadata songMetadata = new SongMetadata();
        HttpConnection postRequest = new HttpConnection();

        JsonArray jsonArray = postRequest.getJsonArray(SERVLET_URL,"POST",fingerprints);
        if(jsonArray != null){
            JsonObject songMetaJO = jsonArray.get(0).getAsJsonObject();
            songMetadata.setFingerprintHit(Integer.parseInt(songMetaJO.get("fingerprintHit").getAsString()));
            songMetadata.setSongId(Integer.parseInt(songMetaJO.get("songId").getAsString()));
            songMetadata.setSongTitle(songMetaJO.get("songTitle").getAsString());
            songMetadata.setOffsetTime(songMetaJO.get("offsetTime").getAsString());
            return songMetadata;
        }
        return null;
    }
}
