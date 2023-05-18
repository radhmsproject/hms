package com.radproject.hms.listAdapters.Suggestions;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;
import java.util.List;

public class AutoFarmSuggestAdapter extends ArrayAdapter<FarmModel> {
    private Context context;
    private ArrayList<FarmModel> originalList;
    private ArrayList<FarmModel> filteredList;
    private LayoutInflater inflater;

    public AutoFarmSuggestAdapter(@NonNull Context context, ArrayList<FarmModel> originalList) {
        super(context, 0, originalList);
        this.context = context;
        this.originalList = originalList;
        inflater = LayoutInflater.from(context);
        filteredList = new ArrayList<>(); // Add this line to initialize the filteredList
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_farms, parent, false);
        }

        FarmModel currentFarm = getItem(position);
        if (currentFarm == null) {
            return convertView;
        }

        ImageView farmImage;
        TextView farmName;
        TextView farmNo;
        TextView farmSize;
        TextView farmLocation;
        ImageButton viewFarmButton;
        farmImage = convertView.findViewById(R.id.farmImage);
        farmName = convertView.findViewById(R.id.FarmNameTV);
        farmNo = convertView.findViewById(R.id.FarmNoTV);
        farmSize = convertView.findViewById(R.id.FarmSizeTV);
        farmLocation = convertView.findViewById(R.id.LocationTV);
        viewFarmButton = convertView.findViewById(R.id.viewFarmBTN);

        farmName.setText(currentFarm.getName());
        farmNo.setText(currentFarm.getFarmId() + "");
        farmSize.setText(currentFarm.getNumOfPerch() + "");
        farmLocation.setText(currentFarm.getLocation());

        return convertView;
    }


    @NonNull
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    // If there is no filter, return the full list of assets
                    results.values = originalList;
                    results.count = originalList.size();
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    ArrayList<FarmModel> filteredItems = new ArrayList<>();

                    for (FarmModel farmModel : originalList) {
                        if (farmModel.getFarmId().toLowerCase().contains(filterPattern)
                                || farmModel.getName().toLowerCase().contains(filterPattern)) {
                            // If any of the attributes match the filter pattern, add the farmModel to the filtered list
                            filteredItems.add(farmModel);
                        }
                    }

                    results.values = filteredItems;
                    results.count = filteredItems.size();
                }

                return results;
            }



            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((ArrayList<FarmModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
