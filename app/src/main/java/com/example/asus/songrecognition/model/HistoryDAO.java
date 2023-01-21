package com.example.asus.songrecognition.model;



import java.util.ArrayList;

public interface HistoryDAO {
    boolean insert(History history);
    ArrayList<History> read();
    boolean delete(int[] id);
}
