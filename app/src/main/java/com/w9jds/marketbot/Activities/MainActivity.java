package com.w9jds.marketbot.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.DataManager;
import com.w9jds.marketbot.ui.animations.GridItemAnimator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MarketGroupsAdapter.onMarketGroupChanged {


    @Bind(R.id.market_groups)
    RecyclerView recyclerView;

    private DataManager dataManager;
    private MarketGroupsAdapter adapter;

    private ArrayList<? extends MarketItemBase> marketGroupList;
    private MarketGroup currentParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                marketGroupList = new ArrayList<>(data);
                adapter.addAndResort(data);
            }
        };

        adapter = new MarketGroupsAdapter(this, dataManager, this);
        recyclerView.setAdapter(adapter);

        dataManager.loadMarketGroups();
    }

    @Override
    public void updateSelectedParentGroup(MarketGroup group) {
        currentParent = group;
        adapter.updateCollection(group.children.values());
    }

    @Override
    public void onBackPressed() {
        if (currentParent != null && currentParent.hasParent()) {
            bfsForParent(currentParent.getParentGroupId());
            adapter.updateCollection(currentParent.children.values());
        }
        else if (currentParent != null) {
            adapter.updateCollection(marketGroupList);
            currentParent = null;
        }
        else {
            super.onBackPressed();
        }
    }

    public void bfsForParent(Integer parentId) {
        Queue queue = new LinkedList();

        for (MarketItemBase group : marketGroupList) {
            queue.add(group);

            while(!queue.isEmpty()) {
                MarketGroup node = (MarketGroup)queue.remove();
                if (node.getId() == parentId) {
                    currentParent = node;
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
