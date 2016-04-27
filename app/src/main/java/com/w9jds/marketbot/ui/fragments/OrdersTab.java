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
import com.w9jds.marketbot.classes.models.StationMargin;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.loader.TabsLoader;
import com.w9jds.marketbot.ui.adapters.OrdersAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Jeremy on 3/1/2016.
 *
 * Modified by Alexander Whipp on 4/8/2016.
 */

public final class ListTab extends Fragment implements BaseDataManager.DataLoadingCallbacks {

    static final String ARG_PAGE = "ARG_PAGE";

    @Bind(R.id.swipe_refresh) SwipeRefreshLayout refreshLayout;
    @Bind(R.id.orders_list) RecyclerView orders;

    private int position;

    private OrdersAdapter adapter;
    private TabsLoader loader;

    private Region currentRegion;
    private Type currentType;

    public static ListTab create(int page, List<MarketOrder> orders) {
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
//        args.putParcelableArrayList("orders", orders);
        ListTab fragment = new ListTab();
        fragment.setArguments(args);
        return fragment;
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
        loader = new TabsLoader(getActivity()) {
            @Override
            public void onSellOrdersLoaded(List<MarketOrder> orders) {

            }

            @Override
            public void onBuyOrdersLoaded(List<MarketOrder> orders) {

            }

            @Override
            public void onMarginsLoaded(List<StationMargin> orders) {

            }
        };


        loader.registerLoadingCallback(this);
//        ((ItemActivity)getActivity()).addOrdersFragment(position, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders_list, container, false);
        ButterKnife.bind(this, view);

        orders.setLayoutManager(new LinearLayoutManager(getContext()));
        orders.setItemAnimator(new DefaultItemAnimator());
        orders.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(() -> updateOrdersList(currentRegion, currentType));



//        adapter.updateCollection();
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
                loader.loadSellOrders(region.getId(), type);
                break;
            case 2:
                loader.loadBuyOrders(region.getId(), type);
                break;
            case 3:
                loader.loadMarginOrders(region.getId(), type);
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