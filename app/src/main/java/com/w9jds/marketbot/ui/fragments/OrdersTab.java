package com.w9jds.marketbot.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.w9jds.marketbot.R;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.loader.OrdersLoader;
import com.w9jds.marketbot.ui.ItemActivity;
import com.w9jds.marketbot.ui.adapters.OrdersAdapter;
import com.w9jds.marketbot.data.BaseDataManager;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 *
 * Modified by Alexander Whipp on 4/8/2016.
 */

public final class OrdersTab extends Fragment implements BaseDataManager.DataLoadingCallbacks {

    static final String ARG_PAGE = "ARG_PAGE";

    @Bind(R.id.swipe_refresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.orders_list) RecyclerView orders;

    private int position;

    private OrdersAdapter adapter;
    private OrdersLoader loader;

    private Region currentRegion;
    private Type currentType;

    public static OrdersTab create(int page) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        OrdersTab fragment = new OrdersTab();
        fragment.setArguments(args);
        return fragment;
    }

    public OrdersTab() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();

        position = args.getInt(ARG_PAGE);

        adapter = new OrdersAdapter(getContext());

        loader = new OrdersLoader(getContext()) {
            @Override
            public void onDataLoaded(List<MarketOrder> orders) {
                adapter.updateCollection(orders);
            }
        };

        loader.registerLoadingCallback(this);
        ((ItemActivity)getActivity()).addOrdersFragment(position, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        ButterKnife.bind(this, view);

        orders.setLayoutManager(new LinearLayoutManager(getContext()));
        orders.setItemAnimator(new DefaultItemAnimator());
        orders.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> updateOrdersList(currentRegion, currentType));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public void updateOrdersList(Region region, Type type) {
        currentRegion = region;
        currentType = type;

        adapter.clear();
        switch(position) {
            case 1:
                loader.loadSellOrders(region, type);
                break;
            case 2:
                loader.loadBuyOrders(region, type);
                break;
            case 3:
                loader.loadMarginOrders(region, type);
                break;
        }
    }

    @Override
    public void dataStartedLoading() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void dataFinishedLoading() {
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void dataFailedLoading(String errorMessage) {

    }

}