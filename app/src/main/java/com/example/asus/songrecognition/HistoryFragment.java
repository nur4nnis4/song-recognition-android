package com.example.asus.songrecognition;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.asus.songrecognition.Presenter.HistoryPresenter;
import com.example.asus.songrecognition.Presenter.HistoryPresenterImpl;
import com.example.asus.songrecognition.Presenter.HistoryView;
import com.example.asus.songrecognition.model.History;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment implements HistoryView {

    private HistoryPresenter historyPresenter;
    private RecyclerView rvHistory;
    private LinearLayout noHistoryNotification;
    private RecyclerView.LayoutManager layoutManager;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        historyPresenter = new HistoryPresenterImpl(this);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvHistory = getView().findViewById(R.id.recycler_view_history);
        noHistoryNotification = getView().findViewById(R.id.no_history_notif);
        layoutManager = new LinearLayoutManager(getActivity());
        rvHistory.setLayoutManager(layoutManager);
        rvHistory.setHasFixedSize(true);
        historyPresenter.loadListItems();
    }

    @Override
    public void showHistory(final ArrayList<History> historyList){
        noHistoryNotification.setVisibility(View.INVISIBLE);
        final HistoryAdapter adapter = new HistoryAdapter(historyList);
        rvHistory.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }
            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int i) {
                int position = target.getAdapterPosition();
                historyPresenter.deleteListItems(new int[]{historyList.get(position).getId()});
                historyList.remove(position);
                adapter.notifyDataSetChanged();
            }
        });

        itemTouchHelper.attachToRecyclerView(rvHistory);
    }

    @Override
    public void showNoHistory() {
        noHistoryNotification.setVisibility(View.VISIBLE);
    }

    @Override
    public Context getAppContext() {return getActivity().getApplicationContext();}

}
