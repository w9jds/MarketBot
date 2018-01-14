//package com.w9jds.marketbot.ui.fragments;
//
//import android.content.Context;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.DefaultItemAnimator;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.w9jds.marketbot.R;
//import com.w9jds.marketbot.ui.adapters.ListAdapter;
//
//import java.util.List;
//import java.util.Map;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//import rx.functions.Action1;
//import rx.subjects.BehaviorSubject;
//import rx.subscriptions.CompositeSubscription;
//
//public final class ListTab extends Fragment {
//
//    static final String ARG_PAGE = "ARG_PAGE";
//
//    @Bind(R.id.orders_list) RecyclerView orders;
//
//    private CompositeSubscription subscriptions;
//
//    private int position;
//    private ListAdapter adapter;
//
//
//    public static ListTab create(int page, BehaviorSubject<Map.Entry<Integer, List<?>>> behaviorSubject) {
//        Bundle args = new Bundle();
//        args.putInt(ARG_PAGE, page);
//
//        ListTab fragment = new ListTab();
//        fragment.setArguments(args);
//
//        fragment.setRetainInstance(true);
//        fragment.getSubscriptions().add(behaviorSubject
//            .doOnError(Throwable::printStackTrace)
//            .doOnNext(fragment.updateTab)
//            .subscribe());
//
//        return fragment;
//    }
//
//    public ListTab() {
//        subscriptions = new CompositeSubscription();
//    }
//
//    public Action1<Map.Entry<Integer, List<?>>> updateTab = marketOrders -> {
//        if (marketOrders.getKey() == position) {
//            adapter.updateCollection(marketOrders.getValue());
//        }
//    };
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    public CompositeSubscription getSubscriptions() {
//        return subscriptions;
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        subscriptions.unsubscribe();
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Bundle args = getArguments();
//        position = args.getInt(ARG_PAGE);
//
//        adapter = new ListAdapter(getContext());
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_market_lists, container, false);
//        ButterKnife.bind(this, view);
//
//        orders.setLayoutManager(new LinearLayoutManager(getContext()));
//        orders.setItemAnimator(new DefaultItemAnimator());
//        orders.setAdapter(adapter);
//
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        ButterKnife.unbind(this);
//    }
//
//}