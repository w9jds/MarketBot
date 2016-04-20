package com.w9jds.marketbot.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.OrdersTabAdapter;
import com.w9jds.marketbot.adapters.RegionAdapter;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.loader.DataManager;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;
import com.w9jds.marketbot.ui.fragments.OrdersTab;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 2/22/16.
 *
 * Modified by Alexander Whipp on 4/8/16.
 */

public class ItemActivity extends AppCompatActivity implements BaseDataManager.DataUpdatingCallbacks,
        AdapterView.OnItemSelectedListener {

    @Inject boolean isFirstRun;

    @Bind(R.id.item_content) CoordinatorLayout baseView;
    @Bind(R.id.main_toolbar) Toolbar toolbar;
    @Bind(R.id.region_spinner) Spinner regionSpinner;
    @Bind(R.id.content_pager) ViewPager pager;
    @Bind(R.id.sliding_tabs) TabLayout tabLayout;

    private ProgressDialog progressDialog;
    private DataManager dataManager;
    private Type currentType;
    private RegionAdapter regionAdapter;
    private OrdersTabAdapter tabAdapter;
    private boolean updateRun = false;
    private long defaultRegionId;
    private long typeIdExtra;

    private HashMap<Integer, OrdersTab> orderTabs = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        ButterKnife.bind(this);

        updateStorageSession();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        SharedPreferences settings = getSharedPreferences("temporary", 0);

        typeIdExtra = intent.getLongExtra("typeId", -1);
        defaultRegionId = settings.getLong("regionId", -1);

        regionAdapter = new RegionAdapter(this);
        dataManager = new DataManager(getApplication()) {
            @Override
            public void onProgressUpdate(final int page, final int totalPages) {
                if (progressDialog.isIndeterminate()) {
                    progressDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            updateProgressDialog(page, totalPages, true);
                        }
                    });

                    progressDialog.dismiss();
                }

                updateProgressDialog(page, totalPages, false);
            }

            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                if (data.size() > 0 && data.get(0) instanceof Region) {
                    regionAdapter.addAllItems(data);

                    if (defaultRegionId == -1) {
                        regionSpinner.setSelection(regionAdapter.getPositionfromId(10000002), true);
                    }
                    else {
                        regionSpinner.setSelection(regionAdapter.getPositionfromId((int)defaultRegionId), true);
                    }
                }

                if (data.size() > 0 && data.get(0) instanceof MarketGroup) {
                    updateStorageSession();
                    checkExtras();
                }
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        dataManager.registerCallback(this);
        regionSpinner.setAdapter(regionAdapter);
        regionSpinner.setOnItemSelectedListener(this);

        checkExtras();
    }

    private void updateStorageSession() {
        MarketBot.createNewStorageSession().inject(this);
    }

    private void updateProgressDialog(int page, int max, boolean isNewWindow) {
        if (isNewWindow) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(false);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMessage("Updating Items Cache...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
        }

        progressDialog.setMax(max);
        progressDialog.setProgress(page);

        if (isNewWindow) {
            progressDialog.show();
        }
    }

    private void checkExtras() {
        if (!isFirstRun) {
            if (typeIdExtra != -1) {
                currentType = MarketTypeEntry.getType(this, typeIdExtra);
            } else {
                currentType = getIntent().getParcelableExtra("currentType");
            }

            loadTypeData();
        }
        else if (!updateRun) {
            dataManager.updateAndLoad();
        }
        else {
            Snackbar.make(baseView, "Update failed, Please try again.", Snackbar.LENGTH_LONG).show();
        }
    }

    private void loadTypeData() {
        tabAdapter = new OrdersTabAdapter(getSupportFragmentManager(), this, currentType.getId());
        pager.setOffscreenPageLimit(4);
        pager.setAdapter(tabAdapter);
        tabLayout.setupWithViewPager(pager);

        tabLayout.setTabsFromPagerAdapter(tabAdapter);

        dataManager.loadRegions(false);
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
    public void dataUpdatingStarted() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Updating Market Cache...");
        progressDialog.setCancelable(false);
        progressDialog.setIndeterminate(true);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    public void dataUpdatingFinished() {
        progressDialog.dismiss();
        updateRun = true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Region region = (Region) regionAdapter.getItem(position);

        SharedPreferences settings = getSharedPreferences("temporary", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putLong("regionId", region.getId());
        editor.apply();

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
