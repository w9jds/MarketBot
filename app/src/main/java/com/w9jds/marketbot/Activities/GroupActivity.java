package com.w9jds.marketbot.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toolbar;

import com.w9jds.eveapi.Models.MarketGroup;
import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.MarketGroupsAdapter;
import com.w9jds.marketbot.data.DataManager;
import com.w9jds.marketbot.ui.widgets.ElasticDragDismissFrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy Shore on 2/22/16.
 */
public class GroupActivity extends AppCompatActivity {

    @Bind(R.id.group_draggable_frame)
    ElasticDragDismissFrameLayout draggableFrame;

    @Bind(R.id.items_list)
    RecyclerView itemsList;

    private DataManager dataManager;
    private MarketGroup parentGroup;
    private MarketGroupsAdapter adapter;
    private ElasticDragDismissFrameLayout.SystemChromeFader systemChromeFader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_group);
        ButterKnife.bind(this);

        systemChromeFader = new ElasticDragDismissFrameLayout.SystemChromeFader(this) {
            @Override
            public void onDragDismissed() {
                finishAfterTransition();
            }
        };

        itemsList.setLayoutManager(new LinearLayoutManager(this));
        itemsList.setItemAnimator(new DefaultItemAnimator());

        dataManager = new DataManager(this) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                adapter.addAndResort(data);
            }
        };

        final Intent intent = getIntent();
        if (intent.hasExtra("MarketGroup")) {

            adapter = new MarketGroupsAdapter(this, dataManager);
            parentGroup = intent.getParcelableExtra("MarketGroup");

            if (parentGroup.children.size() > 0) {
                adapter.addAndResort(parentGroup.children.values());
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        draggableFrame.addListener(systemChromeFader);
    }

    @Override
    protected void onPause() {
        draggableFrame.removeListener(systemChromeFader);
        super.onPause();
    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }

    private final View.OnClickListener backClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            finishAfterTransition();
        }
    };
}
