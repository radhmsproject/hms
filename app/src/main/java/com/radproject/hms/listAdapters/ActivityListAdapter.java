package com.radproject.hms.listAdapters;

import static android.content.ContentValues.TAG;

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

import java.util.ArrayList;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ViewHolder> {

    //    private ArrayList<FarmModel> AllList;
    private ArrayList<ActivityModel> activityList;
    private int clickedPosition = -1;

//    ActivityPlansActivity context;

    public ActivityListAdapter(ArrayList<ActivityModel> activityModels) {
        this.activityList = activityModels;
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

        holder.listItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        private ImageView deleteImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Initialize the views using findViewById()
            listItemLayout = itemView.findViewById(R.id.list_item_activity_LL);
            indexTextView = itemView.findViewById(R.id.list_item_indexTV);
            activityNameTextView = itemView.findViewById(R.id.list_item_activity_nameTV);
            activityDateTextView = itemView.findViewById(R.id.list_item_activity_date_TV);
            deleteImageView = itemView.findViewById(R.id.list_item_delete_RV_IV);
        }

        public void bind(ActivityModel activityModel) {
            indexTextView.setText(String.valueOf(getAdapterPosition() + 1));
            activityNameTextView.setText(activityModel.getActivity_name());
            activityDateTextView.setText(String.valueOf(activityModel.getPlanned_date()));
        }
    }
}
