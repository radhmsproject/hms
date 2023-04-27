package com.radproject.hms.listAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;


import java.util.List;

public class FarmListAdapter extends RecyclerView.Adapter<FarmListAdapter.FarmViewHolder> {

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

        private TextView mFarmNameTV;
        private TextView mFarmNoTV;
        private TextView mFarmSizeTV;
        private TextView mLocationTV;
        private ImageButton mViewFarmBTN;

        public FarmViewHolder(@NonNull View itemView) {
            super(itemView);

            // Find views by id
            mFarmNameTV = itemView.findViewById(R.id.FarmNameTV);
            mFarmNoTV = itemView.findViewById(R.id.FarmNoTV);
            mFarmSizeTV = itemView.findViewById(R.id.FarmSizeTV);
            mLocationTV = itemView.findViewById(R.id.LocationTV);
            mViewFarmBTN = itemView.findViewById(R.id.viewFarmBTN);
        }

        public void bind(FarmModel item) {
            mItem = item;

            // Bind data to item view
            mFarmNameTV.setText(item.getName());
            mFarmNoTV.setText(item.getFarmId());
            mFarmSizeTV.setText(item.getNumOfPerch()+"");
            mLocationTV.setText(item.getLocation());

            // Set up click listener for view farm button
            mViewFarmBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle button click
                }
            });
        }
    }
}


