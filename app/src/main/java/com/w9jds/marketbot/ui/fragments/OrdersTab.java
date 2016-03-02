package com.w9jds.marketbot.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.adapters.OrdersAdapter;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class OrdersTab extends Fragment implements onRegionChanged, BaseDataManager.DataLoadingCallbacks {

    static final String ARG_PAGE = "ARG_PAGE";



    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    private int position;

    private RecyclerView orders;
    private OrdersAdapter adapter;
    private DataManager dataManager;

    public static OrdersTab create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        OrdersTab fragment = new OrdersTab();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        position = args.getInt(ARG_PAGE);

        dataManager = new DataManager(getContext()) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
//                adapter.updateCollection(data);
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_fragment_layout, container, false);
        ButterKnife.bind(view);

        orders = ButterKnife.findById(view, R.id.orders_list);
        orders.setLayoutManager(new LinearLayoutManager(getContext()));
        orders.setItemAnimator(new DefaultItemAnimator());

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void updateOrdersList(Region region, Type type) {
        switch(position) {
            case 0:
                dataManager.loadSellOrders(region, type);
                break;
            case 1:
                dataManager.loadBuyOrders(region, type);
                break;
        }
    }

    @Override
    public void dataStartedLoading() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void dataFinishedLoading() {
        refreshLayout.setRefreshing(false);
    }
}