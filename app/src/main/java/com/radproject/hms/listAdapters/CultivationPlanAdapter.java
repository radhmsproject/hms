package com.radproject.hms.listAdapters;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.CultivationPlanModel;
import com.radproject.hms.screensFragments.ActivityPlansFragment;
import com.radproject.hms.subActivities.ActivityPlansActivity;

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
        holder.plan_itemLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create a bundle to pass the cultivationPlan data
                Bundle bundle = new Bundle();
                bundle.putSerializable("cultivationPlan", cultivationPlan);

                // Start the ActivityPlanActivity and pass the bundle
                Intent intent = new Intent(v.getContext(), ActivityPlansActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);

            }
        });
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
        LinearLayout plan_itemLL;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            plan_itemLL = itemView.findViewById(R.id.plan_itemLL);
            cultivationPlanNameTextView = itemView.findViewById(R.id.cultivationPlanNameTextView);
            cultivationCropIdTextView = itemView.findViewById(R.id.cultivationCropIdTextView);
            cultivationStartDateTextView = itemView.findViewById(R.id.cultivationStartDateTextView);
            cultivationEndDateTextView = itemView.findViewById(R.id.cultivationEndDateTextView);
            cultivationStatusTextView = itemView.findViewById(R.id.cultivationStatusTextView);
        }
    }
}
