package com.example.asus.songrecognition.Presenter;

import android.content.Context;

public interface IdentifyView {
    void setTVResultText(String text);
    void stopPulsatorLayout();
    void startPulsatorLayout();
    boolean checkPermission();
    void setTvNotificationText(String text);
    void requestPermission();
    Context getAppContext();
}
