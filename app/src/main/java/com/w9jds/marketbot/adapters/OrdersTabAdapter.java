package com.w9jds.marketbot.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.ui.fragments.OrdersTab;
import com.w9jds.marketbot.ui.fragments.TypeInfoTab;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class OrdersTabAdapter extends FragmentStatePagerAdapter {

    private Resources resources;
    private long typeId;

    final int PAGE_COUNT = 3;

    public OrdersTabAdapter(FragmentManager fragmentManager, Context context, long typeId) {
        super(fragmentManager);
        resources = context.getResources();
        this.typeId = typeId;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        switch(position) {
            case 0:
                return resources.getString(R.string.type_info_label);
            case 1:
                return resources.getString(R.string.sell_orders);
            case 2:
                return resources.getString(R.string.buy_orders);
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TypeInfoTab.create(position, typeId);
        }
        else {
            return OrdersTab.create(position);
        }

    }
}