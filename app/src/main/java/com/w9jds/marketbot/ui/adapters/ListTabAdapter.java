package com.w9jds.marketbot.ui.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;
import com.w9jds.marketbot.ui.fragments.ListTab;
import com.w9jds.marketbot.ui.fragments.TypeInfoTab;

import java.util.List;
import java.util.Map;

import rx.subjects.BehaviorSubject;

public final class ListTabAdapter extends FragmentStatePagerAdapter {

    private final Resources resources;
    private final BehaviorSubject<Map.Entry<Integer, List<?>>> subject;
    private final Type type;

    final int PAGE_COUNT = 4;

    public ListTabAdapter(FragmentManager fragmentManager, Context context, Type type,
                          BehaviorSubject<Map.Entry<Integer, List<?>>> subject) {
        super(fragmentManager);
        this.resources = context.getResources();
        this.type = type;
        this.subject = subject;
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
            case 3:
                return resources.getString(R.string.margins);
            default:
                return "";
        }
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return TypeInfoTab.create(type);
        }
        else {
            return ListTab.create(position, subject);
        }
    }
}