package com.w9jds.marketbot.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.marketbot.R;

import java.util.ArrayList;
import java.util.Collection;

import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class RegionAdapter extends BaseAdapter implements SpinnerAdapter {

    private Context context;
    private ArrayList<Region> regions = new ArrayList<>();

    public RegionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    public int getPositionfromId(int id) {
        int size = regions.size();
        for (int i = 0; i < size; i++) {
            Region region = regions.get(i);
            if (region.getId() == id) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int getCount() {
        return regions.size();
    }

    @Override
    public Object getItem(int position) {
        return regions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    static class RegionViewHolder {

        TextView name;

        public RegionViewHolder(View view) {
            name = ButterKnife.findById(view, R.id.region_name);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RegionViewHolder holder;
        final Region region = (Region) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.toolbar_spinner_item_actionbar, parent, false);

            holder = new RegionViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (RegionViewHolder) convertView.getTag();
        }

        holder.name.setText(region.getName());

        return convertView;
    }

    public void addAllItems(Collection<? extends MarketItemBase> regions) {
        for (MarketItemBase item : regions) {
            this.regions.add((Region)item);
        }

        notifyDataSetChanged();
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        RegionViewHolder holder;
        final Region region = (Region) getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.toolbar_spinner_item_dropdown, parent, false);

            holder = new RegionViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (RegionViewHolder) convertView.getTag();
        }

        holder.name.setText(region.getName());

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.toolbar_spinner_item_actionbar;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}