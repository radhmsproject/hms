package com.radproject.hms.listAdapters.Suggestions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;
import java.util.List;

public class AutoFarmSuggestAdapter extends ArrayAdapter<FarmModel> {
    private List<FarmModel> originalList;
    private List<FarmModel> filteredList;
    private LayoutInflater inflater;

    public AutoFarmSuggestAdapter(Context context, List<FarmModel> originalList) {
        super(context, 0, originalList);
        this.originalList = new ArrayList<>(originalList);
        this.filteredList = new ArrayList<>();
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.list_item_farms, parent, false);
        }

        FarmModel farm = getItem(position);
        if (farm != null) {
            ImageView farmImage = convertView.findViewById(R.id.farmImage);
            TextView farmName = convertView.findViewById(R.id.FarmNameTV);
            TextView farmNo = convertView.findViewById(R.id.FarmNoTV);
            TextView farmSize = convertView.findViewById(R.id.FarmSizeTV);
            TextView farmLocation = convertView.findViewById(R.id.LocationTV);
            ImageButton viewFarmButton = convertView.findViewById(R.id.viewFarmBTN);
            viewFarmButton.setVisibility(View.GONE);
            farmImage.setVisibility(View.GONE);
            // Set the farm data to the views
            farmName.setText(farm.getName());
            farmNo.setText(farm.getFarmId() + "");
            farmSize.setText(farm.getNumOfPerch() + "");
            farmLocation.setText(farm.getLocation());

        }
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();
                filteredList.clear();

                if (constraint != null) {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (FarmModel farm : originalList) {
                        if (farm.getName().toLowerCase().contains(filterPattern)
                                || farm.getFarmId().toLowerCase().contains(filterPattern)) {
                            filteredList.add(farm);
                        }
                    }
                    filterResults.values = filteredList;
                    filterResults.count = filteredList.size();
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                if (results.values != null) {
                    addAll((ArrayList<FarmModel>) results.values);
                    notifyDataSetChanged();
                }
            }
        };
    }
}