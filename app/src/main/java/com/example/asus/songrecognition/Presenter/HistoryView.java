package com.example.asus.songrecognition.Presenter;

import android.content.Context;

import com.example.asus.songrecognition.model.History;

import java.util.ArrayList;

public interface HistoryView {
    Context getAppContext();
    void showHistory(ArrayList<History> historyList);
    void showNoHistory();
}
