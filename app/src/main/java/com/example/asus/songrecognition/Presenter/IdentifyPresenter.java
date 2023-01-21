package com.example.asus.songrecognition.Presenter;

import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.example.asus.songrecognition.model.History;
import com.example.asus.songrecognition.model.HistoryDAO;
import com.example.asus.songrecognition.model.HistoryDAOImpl;
import com.example.asus.songrecognition.fingerprint.ExtractFingerprint;
import com.example.asus.songrecognition.model.InternetConnectionHandler;
import com.example.asus.songrecognition.model.RecordWav;
import com.example.asus.songrecognition.model.SongFingerprints;
import com.example.asus.songrecognition.model.SongMetadata;
import com.example.asus.songrecognition.model.SongMetadataDAO;
import com.example.asus.songrecognition.model.SongMetadataDAOImpl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class IdentifyPresenter {

    private IdentifyView identifyView;
    private Handler handler = new Handler();
    private RecordWav record = new RecordWav();
    private IdentifySong identifyTask;
    private long startTime;
    private long finishTime;
    private double runningTime;
    private int repeatIdentification;

    public IdentifyPresenter(IdentifyView identifyView) {
        this.identifyView = identifyView;
    }

    public void start(){
        repeatIdentification = 3;

        if(!record.isRecording && checkInternetConnection()) {
            identifyView.startPulsatorLayout();
            startTime = System.currentTimeMillis();
            startRecording();
        }
        else {
            identifyView.stopPulsatorLayout();
            record.stopRecording();
            handler.removeCallbacksAndMessages(null);
            try{
                identifyTask.cancel(true);
            }catch (Exception e){

            }
        }
    }

    private class IdentifySong extends AsyncTask<String,String,SongMetadata>{

        @Override
        protected SongMetadata doInBackground(String... filePath) {
            ExtractFingerprint ef = new ExtractFingerprint();
            ArrayList<SongFingerprints> fingerprints= null;
            try {
                fingerprints = ef.getFingerPrint(filePath[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            SongMetadataDAO songMetadataDAO = new SongMetadataDAOImpl();
            return songMetadataDAO.SearchMetaData(fingerprints);
        }

        @Override
        protected void onPostExecute(SongMetadata songMetadata) {
            super.onPostExecute(songMetadata);

            if(songMetadata != null){
                Log.d("Hits ", ""+ repeatIdentification +"| "+songMetadata.getFingerprintHit());
                if(songMetadata.getSongId() !=0){
                    showResults(songMetadata);
                    new InsertHistory().execute(songMetadata.getSongTitle());
                }
                else {
                    if(repeatIdentification>0){
                        startRecording();
                        repeatIdentification--;
                    }
                    else {
                        showResults(songMetadata);
                    }
                }
            }
            else {
                identifyView.setTvNotificationText("Can't connect to server !");
                identifyView.stopPulsatorLayout();
            }
        }
    }

    private void startRecording(){
        if(checkInternetConnection()){
            if (identifyView.checkPermission()) {

                record.startRecording();
                int recordDuration = 2000; //2 seconds

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        record.stopRecording();
                        identifyTask = new IdentifySong();
                        identifyTask.execute(record.getRawFilename());
                    }
                }, recordDuration);

            } else {
                identifyView.requestPermission();
            }
        }
    }
    public void showResults(SongMetadata songMetadata){

        finishTime = System.currentTimeMillis();
        runningTime = (finishTime - startTime)*0.001;
        Log.d("RUNNING TIME ", runningTime+"s");

        identifyView.setTVResultText(songMetadata.getSongTitle());
        identifyView.stopPulsatorLayout();
        repeatIdentification = 3;
    }

    private class InsertHistory extends AsyncTask<String,String,Boolean>{

        @Override
        protected Boolean doInBackground(String... songTitle) {

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            History history = new History();
            history.setSongTitle(songTitle[0]);
            history.setDate(dateFormat.format(calendar.getTime()));

            HistoryDAO historyDAO = new HistoryDAOImpl(identifyView.getAppContext());
            return historyDAO.insert(history);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            if(isSuccess)
                Log.d("INSERT HISTORY","SUCCESS" );
            else
                Log.d("INSERT HISTORY", "FAILED");
        }
    }

    public boolean checkInternetConnection(){
        InternetConnectionHandler handler = new InternetConnectionHandler(identifyView.getAppContext());
        if (!handler.isConnected()){
            identifyView.setTvNotificationText("Please check your internet connection.");
        }
        else
            identifyView.setTvNotificationText("");
        return handler.isConnected();
    }





}
