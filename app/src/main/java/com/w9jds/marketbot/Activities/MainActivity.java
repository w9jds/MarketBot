package com.w9jds.marketbot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MarketGroupsAdapter.onMarketGroupChanged,
        BaseDataManager.DataLoadingCallbacks{

    @Bind(R.id.market_groups)
    RecyclerView recyclerView;

    @Bind(R.id.dataloading_progress)
    ProgressBar progressBar;

    private DataManager dataManager;
    private MarketGroupsAdapter adapter;
    private LinearLayoutManager layoutManager;

    private ArrayList<? extends MarketItemBase> marketGroupList;
    private MarketGroup currentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                if (marketGroupList == null) {
                    marketGroupList = new ArrayList<>(data);
                }

                adapter.addAndResort(data);
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        dataManager.registerCallback(this);

        adapter = new MarketGroupsAdapter(this, dataManager, this);
        recyclerView.setAdapter(adapter);

        dataManager.loadMarketGroups();
    }

    @Override
    public void updateSelectedParentGroup(MarketGroup group) {
        currentParent = group;

        if (group.children.size() > 0) {
            adapter.updateCollection(group.children.values());
        }
        else if (group.items.size() < 1) {
            adapter.clear();
            dataManager.loadGroupTypes(group.getTypesLocation());
        }
        else {
            adapter.updateCollection(group.items.values());
        }

        layoutManager.scrollToPosition(0);
    }

    @Override
    public void onBackPressed() {
        if (!dataManager.isDataLoading()) {
            if (currentParent != null && currentParent.hasParent()) {
                bfsForParent(currentParent.getParentGroupId());
                adapter.updateCollection(currentParent.children.values());
            } else if (currentParent != null) {
                adapter.updateCollection(marketGroupList);
                currentParent = null;
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    public void dataStartedLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void dataFinishedLoading() {
        progressBar.setVisibility(View.GONE);
    }

    private void bfsForParent(long parentId) {
        boolean parentFound = false;
        Queue queue = new LinkedList();

        for (MarketItemBase group : marketGroupList) {
            if (parentFound) {
                break;
            }

            queue.add(group);

            while(!queue.isEmpty()) {
                MarketGroup node = (MarketGroup)queue.remove();
                if (node.getId() == parentId) {
                    currentParent = node;
                    parentFound = true;
                    queue.clear();
                }
                else {
                    for (MarketGroup child : node.children.values()) {
                        queue.add(child);
                    }
                }
            }
        }
    }
}
