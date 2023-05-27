package com.radproject.hms.listAdapters;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
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
import com.radproject.hms.models.ActivityModel;
import com.radproject.hms.subActivities.CostingActivity;

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {

    //    private ArrayList<FarmModel> AllList;
    private ArrayList<ActivityModel> activityList;
    private int clickedPosition = -1;
    private Bundle bundle;

//    ActivityPlansActivity context;

    public ActivityListAdapter(ArrayList<ActivityModel> activityModels, Bundle bundle) {
        this.activityList = activityModels;
        this.bundle = bundle;
        Log.e("TAG", "SelectedFarmAdapter2: " + activityModels.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_activity, parent, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ActivityModel activityModel = activityList.get(position);
        Log.e(TAG, "RV ADAPTER: " + position);
        holder.bind(activityModel);

        holder.list_item_edit_costImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                bundle.putSerializable("activity_model", activityModel); // Replace "activityId" with the appropriate key and value

                // Start the CostingActivity and pass the bundle
                Intent intent = new Intent(v.getContext(), CostingActivity.class);
                intent.putExtras(bundle);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout listItemLayout;
        private TextView indexTextView;
        private TextView activityNameTextView;
        private TextView activityDateTextView;
        private ImageView list_item_edit_costImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views using findViewById()
            listItemLayout = itemView.findViewById(R.id.list_item_activity_LL);
            indexTextView = itemView.findViewById(R.id.list_item_indexTV);
            activityNameTextView = itemView.findViewById(R.id.list_item_activity_nameTV);
            activityDateTextView = itemView.findViewById(R.id.list_item_activity_date_TV);
            list_item_edit_costImageView = itemView.findViewById(R.id.list_item_edit_cost);
        }

        public void bind(ActivityModel activityModel) {
            indexTextView.setText(String.valueOf(getAdapterPosition() + 1));
            activityNameTextView.setText(activityModel.getActivity_name());
            activityDateTextView.setText(String.valueOf(activityModel.getPlanned_date()));
        }
    }
}
