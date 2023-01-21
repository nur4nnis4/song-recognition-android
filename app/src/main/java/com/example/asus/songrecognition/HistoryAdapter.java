package com.example.asus.songrecognition;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.songrecognition.model.History;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    private ArrayList<History> historyList;
    private OnItemClickListener itemClickListener;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
    public HistoryAdapter(ArrayList<History> historyList){
        this.historyList = historyList;
    }

    public static class HistoryViewHolder extends RecyclerView.ViewHolder{
        public TextView tvTitle;
        public TextView tvDate;

        public HistoryViewHolder(@NonNull View itemView, final OnItemClickListener itemClickListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDate =  itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (itemClickListener!=null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            itemClickListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_layout,viewGroup, false);
        return new HistoryViewHolder(view,itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, int i) {
        History history = historyList.get(i);
        historyViewHolder.tvTitle.setText(history.getSongTitle());
        historyViewHolder.tvDate.setText(history.getDate());


    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }




}
