package com.radproject.hms.listAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;


import java.util.List;

public class FarmListAdapter  extends RecyclerView.Adapter<FarmListAdapter.FarmViewHolder> {

    private FarmModel[] listData;

    public FarmListAdapter(FarmModel[] listData) {
       this.listData = listData;
    }

    @NonNull
    @Override
    public FarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.list_item_farms, parent, false);
        return new FarmViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FarmViewHolder holder,
                                 int position) {
        FarmModel item = listData[position];
        holder.bind(item);
    }


    @Override
    public int getItemCount() {
        return listData.length;
    }

    public class FarmViewHolder extends RecyclerView.ViewHolder {

        private FarmModel mItem;

        public FarmViewHolder(@NonNull View itemView) {
            super(itemView);

            // Set up item view
        }

        public void bind(FarmModel item) {
            mItem = item;

            // Bind data to item view
        }
    }
}


