package com.radproject.hms.listAdapters;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;
import com.radproject.hms.subActivities.ActivityPlansActivity;
import com.radproject.hms.subActivities.CultivationPlanActivity;

import java.util.ArrayList;

public class SelectedFarmAdapter2 extends RecyclerView.Adapter<SelectedFarmAdapter2.ViewHolder> {

    private ArrayList<FarmModel> AllList;
    private ArrayList<FarmModel> AddedList;
    private int clickedPosition = -1;

    ActivityPlansActivity context;


    public SelectedFarmAdapter2(ArrayList<FarmModel> addedFarms) {
        this.AddedList = addedFarms;

        Log.e("TAG", "SelectedFarmAdapter2: " + addedFarms.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_added_farms, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmModel farm = AllList.get(position);
        holder.bind(farm);
        holder.deleteIV.setVisibility(View.GONE);
        holder.selectedFarmLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickedPosition != position) {
                    int previousClickedPosition = clickedPosition;
                    clickedPosition = position;
                    notifyDataSetChanged();

                    // Stop the blinking after 5 seconds
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            clickedPosition = -1;
                            notifyItemChanged(previousClickedPosition);
                        }
                    }, 5000);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return AllList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView farmIndexTV;
        private TextView farmNameTV;
        private TextView farmSizeTV;
        private ImageView deleteIV;
        private LinearLayout selectedFarmLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            farmIndexTV = itemView.findViewById(R.id.list_item_farm_indexTV);
            farmNameTV = itemView.findViewById(R.id.list_item_farm_nameTV);
            farmSizeTV = itemView.findViewById(R.id.list_item_farm_size_TV);
            deleteIV = itemView.findViewById(R.id.list_item_delete_RV_IV);
            selectedFarmLL = itemView.findViewById(R.id.list_item_selected_farm_LL);
        }

        public void bind(FarmModel farm) {
            farmIndexTV.setText(String.valueOf(getAdapterPosition() + 1));
            farmNameTV.setText(farm.getName());
            farmSizeTV.setText(String.valueOf(farm.getNumOfPerch()));
        }
    }
}
