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

import com.w9jds.eveapi.Models.MarketItemBase;
import com.w9jds.eveapi.Models.MarketOrder;
import com.w9jds.eveapi.Models.Region;
import com.w9jds.eveapi.Models.Type;
import com.w9jds.marketbot.R;
import com.w9jds.marketbot.activities.ItemActivity;
import com.w9jds.marketbot.adapters.OrdersAdapter;
import com.w9jds.marketbot.classes.Triplet;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 */
public final class OrdersTab extends Fragment implements BaseDataManager.DataLoadingCallbacks {

    static final String ARG_PAGE = "ARG_PAGE";

    @Bind(R.id.swipe_refresh)
    SwipeRefreshLayout refreshLayout;

    @Bind(R.id.orders_list)
    RecyclerView orders;

    private int position;

    private OrdersAdapter adapter;
    private DataManager dataManager;

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
        dataManager = new DataManager(getActivity().getApplication()) {
            @Override
            public void onDataLoaded(List<? extends MarketItemBase> data) {
                if (data.size() > 0) {
                    if (data.get(0) instanceof MarketOrder) {
                        ArrayList<MarketOrder> orders = new ArrayList<>();
                        for (MarketItemBase base : data) {
                            orders.add((MarketOrder) base);
                        }

                        adapter.updateCollection(orders);
                    }
                }
            }

            @Override
            public void onDataLoaded(Object data) {
                // never fired
            }
        };

        dataManager.registerCallback(this);
        ((ItemActivity)getActivity()).addOrdersFragment(position, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        ButterKnife.bind(this, view);

        orders.setLayoutManager(new LinearLayoutManager(getContext()));
        orders.setItemAnimator(new DefaultItemAnimator());
        orders.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                updateOrdersList(currentRegion, currentType);
            }
        });

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
                dataManager.loadSellOrders(region, type);
                break;
            case 2:
                dataManager.loadBuyOrders(region, type);
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


}