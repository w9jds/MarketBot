package com.w9jds.marketbot.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.OrdersTabAdapter;
import com.w9jds.marketbot.adapters.RegionAdapter;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;
import com.w9jds.marketbot.ui.fragments.OrdersTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 2/22/16.
 */
public class ItemActivity extends AppCompatActivity implements BaseDataManager.DataLoadingCallbacks, AdapterView.OnItemSelectedListener {

    @Bind(R.id.region_spinner)
    Spinner regionSpinner;

    @Bind(R.id.content_pager)
    ViewPager pager;

    @Bind(R.id.sliding_tabs)
    TabLayout tabLayout;

    private ActionBar actionBar;
    private DataManager dataManager;
    private Type currentType;
    private RegionAdapter regionAdapter;
    private OrdersTabAdapter tabAdapter;

    private HashMap<Integer, OrdersTab> orderTabs = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        ButterKnife.bind(this);

        Toolbar toolbar = ButterKnife.findById(this, R.id.main_toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        currentType = getIntent().getParcelableExtra("currentType");
        regionAdapter = new RegionAdapter(this);
        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                if (data.size() > 0) {
                    if (data.get(0) instanceof Region) {
                        regionAdapter.addAllItems(data);

                        regionSpinner.setSelection(regionAdapter.getPositionfromId(10000002), true);
                    }
                }
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        regionSpinner.setAdapter(regionAdapter);
        regionSpinner.setOnItemSelectedListener(this);

        tabAdapter = new OrdersTabAdapter(getSupportFragmentManager(), this, currentType.getId());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(pager);
        tabLayout.setTabsFromPagerAdapter(tabAdapter);

        dataManager.loadRegions();
    }

    public String getCurrentTypeIcon() {
        return currentType.getIconLink();
    }

    public String getCurrentTypeName() {
        return currentType.getName();
    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }

    @Override
    public void dataStartedLoading() {

    }

    @Override
    public void dataFinishedLoading() {

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Region region = (Region) regionAdapter.getItem(position);

        for (OrdersTab tab : orderTabs.values()) {
            tab.updateOrdersList(region, currentType);
        }
    }

    public void addOrdersFragment(int position, OrdersTab tab) {
        orderTabs.put(position, tab);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
