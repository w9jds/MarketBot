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

import butterknife.Bind;
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
        return regions.get(position).getId();
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    class ViewHolder {

        @Bind(R.id.region_name)
        TextView name;

        public ViewHolder(View view) {
            ButterKnife.bind(view);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Region region = (Region) getItem(position);
        final int layout = getItemViewType(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(layout, parent, false);

            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
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
        return getView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return R.layout.simple_spinner_item;
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