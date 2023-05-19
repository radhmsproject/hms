package com.radproject.hms.listAdapters.Suggestions;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.radproject.hms.R;
import com.radproject.hms.models.FarmModel;

import java.util.ArrayList;
import java.util.List;

public class AutoFarmSuggestAdapter extends BaseAdapter implements Filterable {
    private Context context;
    private List<FarmModel> dataList;
    private List<FarmModel> filteredList;

    public AutoFarmSuggestAdapter(Context context, List<FarmModel> dataList) {
        this.context = context;
        this.dataList = dataList;
        this.filteredList = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public FarmModel getItem(int position) {
        return filteredList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_farms, parent, false);
        }

        FarmModel farm = getItem(position);
        if (farm != null) {
            ImageView farmImage = convertView.findViewById(R.id.farmImage);
            TextView farmName = convertView.findViewById(R.id.FarmNameTV);
            TextView farmNo = convertView.findViewById(R.id.FarmNoTV);
            TextView farmSize = convertView.findViewById(R.id.FarmSizeTV);
            TextView farmLocation = convertView.findViewById(R.id.LocationTV);
            ImageButton viewFarmButton = convertView.findViewById(R.id.viewFarmBTN);

            // Set the farm data to the views
            farmName.setText(farm.getName());
            farmNo.setText(farm.getFarmId()+"");
            farmSize.setText(farm.getNumOfPerch()+"");
            farmLocation.setText(farm.getLocation());

            // Set click listener for the view farm button
            viewFarmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Handle the click event
                }
            });
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
                    for (FarmModel farm : dataList) {
                        if (farm.getName().toLowerCase().contains(filterPattern)) {
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
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }
}
