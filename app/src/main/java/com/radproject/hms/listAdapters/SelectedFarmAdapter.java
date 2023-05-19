package com.radproject.hms.listAdapters;

import android.os.Handler;
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
import com.radproject.hms.subActivities.CultivationPlanActivity;

import java.util.ArrayList;

public class SelectedFarmAdapter extends RecyclerView.Adapter<SelectedFarmAdapter.ViewHolder> {

    private ArrayList<FarmModel> assetList;
    private ArrayList<FarmModel> suggestAssets;
    private int clickedPosition = -1;
    private CultivationPlanActivity context;

    public SelectedFarmAdapter(ArrayList<FarmModel> assetList,
                               ArrayList<FarmModel> suggestAssets,
                               CultivationPlanActivity context) {
        this.assetList = assetList;
        this.suggestAssets = suggestAssets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_added_farms, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FarmModel farm = assetList.get(position);
        holder.bind(farm);
        holder.deleteIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItem(farm, position);
            }
        });
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
        return assetList.size();
    }

    private void removeItem(FarmModel farm, int position) {
        assetList.remove(farm);
        suggestAssets.add(farm);
        notifyDataSetChanged();
        context.updateAdapter();
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
