package com.example.asus.songrecognition.model;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class InternetConnectionHandler {
    Context context;
    public InternetConnectionHandler(Context context){
        this.context = context;
    }

    public boolean isConnected(){
        ConnectivityManager connectivity =
                (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo inf = connectivity.getActiveNetworkInfo();
            if (inf != null) {
                if (inf.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }


}
