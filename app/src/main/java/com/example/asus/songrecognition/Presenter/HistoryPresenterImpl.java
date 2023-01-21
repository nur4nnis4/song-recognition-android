package com.example.asus.songrecognition.Presenter;


import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.songrecognition.model.History;
import com.example.asus.songrecognition.model.HistoryDAO;
import com.example.asus.songrecognition.model.HistoryDAOImpl;

import java.util.ArrayList;

public class HistoryPresenterImpl implements HistoryPresenter{
    private HistoryView historyView;
    private HistoryDAO historyDAO;

    public HistoryPresenterImpl(HistoryView historyView){
        this.historyView = historyView;
        historyDAO = new HistoryDAOImpl(historyView.getAppContext());
    }

    @Override
    public void loadListItems() {
        new LoadListItems().execute();
    }


    public class LoadListItems extends AsyncTask<Void,Void,ArrayList<History>>{

        @Override
        protected ArrayList<History> doInBackground(Void... voids) {
            ArrayList<History> historyList ;
            historyList = historyDAO.read();
            return historyList;
        }

        @Override
        protected void onPostExecute(ArrayList<History> historyList) {
            super.onPostExecute(historyList);
            if(historyList != null)
                historyView.showHistory(historyList);
            else
                historyView.showNoHistory();
        }
    }

    @Override
    public void deleteListItems(int[] id) {
        new DeleteListItems().execute(id);
    }

    public class DeleteListItems extends AsyncTask<int[],String ,Boolean>{

        @Override
        protected Boolean doInBackground(int[]... ids) {
            return historyDAO.delete(ids[0]);
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess)
                Log.d("DELETE HISTORY", "Success");
            else
                Log.d("DELETE HISTORY", "failed");
            new LoadListItems().execute();
        }
    }

}
