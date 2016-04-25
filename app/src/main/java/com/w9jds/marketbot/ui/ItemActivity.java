package com.w9jds.marketbot.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketGroup;
import com.w9jds.marketbot.classes.models.MarketItemBase;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.classes.models.TypeInfo;
import com.w9jds.marketbot.data.DataLoadingSubject;
import com.w9jds.marketbot.data.loader.GroupsLoader;
import com.w9jds.marketbot.data.loader.OrdersLoader;
import com.w9jds.marketbot.data.loader.TypeLoader;
import com.w9jds.marketbot.ui.adapters.OrdersTabAdapter;
import com.w9jds.marketbot.ui.adapters.RegionAdapter;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;
import com.w9jds.marketbot.ui.fragments.OrdersTab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 2/22/16.
 *
 * Modified by Alexander Whipp on 4/8/16.
 */

public class ItemActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
        DataLoadingSubject.DataLoadingCallbacks, DataLoadingSubject.DataUpdatingCallbacks {

    @Inject boolean isFirstRun;

    @Bind(R.id.item_content) CoordinatorLayout baseView;
    @Bind(R.id.main_toolbar) Toolbar toolbar;
    @Bind(R.id.region_spinner) Spinner regionSpinner;
    @Bind(R.id.content_pager) ViewPager pager;
    @Bind(R.id.sliding_tabs) TabLayout tabLayout;

    private ProgressDialog progressDialog;
    private TypeLoader loader;
    private GroupsLoader updateLoader;
    private Type currentType;
    private RegionAdapter regionAdapter;
    private OrdersTabAdapter tabAdapter;
    private boolean updateRun = false;
    private long defaultRegionId;
    private long typeIdExtra;

    private SparseArray<OrdersTab> orderTabs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_item);
        ButterKnife.bind(this);

        updateStorageSession();
        orderTabs = new SparseArray<>();

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }

        Intent intent = getIntent();
        typeIdExtra = intent.getLongExtra("typeId", -1);
        defaultRegionId = intent.getLongExtra("regionId", -1);

        regionAdapter = new RegionAdapter(this);
        loader = new TypeLoader(this) {
            @Override
            public void onTypeInfoLoaded(TypeInfo info) {

            }

            @Override
            public void onRegionsLoaded(List<Region> regions) {
                regionAdapter.addAllItems(regions);

                if (defaultRegionId == -1) {
                    regionSpinner.setSelection(regionAdapter.getPositionfromId(10000002), true);
                }
                else {
                    regionSpinner.setSelection(regionAdapter.getPositionfromId((int)defaultRegionId), true);
                }
            }
        };

        updateLoader = new GroupsLoader(this) {
            @Override
            public void onProgressUpdate(int page, int totalPages) {
                if (progressDialog.isIndeterminate()) {
                    progressDialog.setOnDismissListener(dialog ->
                            updateProgressDialog(page, totalPages, true));

                    progressDialog.dismiss();
                }

                updateProgressDialog(page, totalPages, false);
            }

            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {

            }
        };

        loader.registerLoadingCallback(this);
        updateLoader.registerUpdatingCallback(this);
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
                currentType = MarketTypeEntry.getType(typeIdExtra);
            } else {
                currentType = getIntent().getParcelableExtra("currentType");
            }

            loadTypeData();
        }
        else if (!updateRun) {
            updateLoader.update();
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

        loader.loadRegions(false);
    }

//    public String getCurrentTypeIcon() {
//        return currentType.getIconLink();
//    }

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

        updateStorageSession();
        checkExtras();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Region region = (Region) regionAdapter.getItem(position);
//
//        for (OrdersTab tab : orderTabs.) {
//            tab.updateOrdersList(region, currentType);
//        }
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

    @Override
    public void dataStartedLoading() {

    }

    @Override
    public void dataFinishedLoading() {

    }

    @Override
    public void dataFailedLoading(String errorMessage) {

    }
}
