package com.radproject.hms.listAdapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.CultivationPlanModel;
import java.util.ArrayList;
import java.util.Arrays;

public class CultivationPlanAdapter extends RecyclerView.Adapter<CultivationPlanAdapter.ViewHolder> {

    private ArrayList<CultivationPlanModel> cultivationPlanList;

    public CultivationPlanAdapter(CultivationPlanModel[] cultivationPlanArray) {
        this.cultivationPlanList = new ArrayList<>(Arrays.asList(cultivationPlanArray));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_cultivation_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CultivationPlanModel cultivationPlan = cultivationPlanList.get(position);

        holder.cultivationPlanNameTextView.setText(cultivationPlan.getCultivation_Plan_name());
        holder.cultivationCropIdTextView.setText(cultivationPlan.getCultivation_CROP_ID());
        holder.cultivationStartDateTextView.setText(cultivationPlan.getCultivation_Start_date());
        holder.cultivationEndDateTextView.setText(cultivationPlan.getCultivation_End_date());
        holder.cultivationStatusTextView.setText(cultivationPlan.getStatus());
    }

    @Override
    public int getItemCount() {
        return cultivationPlanList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView cultivationPlanNameTextView;
        TextView cultivationCropIdTextView;
        TextView cultivationStartDateTextView;
        TextView cultivationEndDateTextView;
        TextView cultivationStatusTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cultivationPlanNameTextView = itemView.findViewById(R.id.cultivationPlanNameTextView);
            cultivationCropIdTextView = itemView.findViewById(R.id.cultivationCropIdTextView);
            cultivationStartDateTextView = itemView.findViewById(R.id.cultivationStartDateTextView);
            cultivationEndDateTextView = itemView.findViewById(R.id.cultivationEndDateTextView);
            cultivationStatusTextView = itemView.findViewById(R.id.cultivationStatusTextView);
        }
    }
}
