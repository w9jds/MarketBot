package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;

import com.w9jds.marketbot.classes.CrestMapper;
import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.StationMargin;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.BaseDataManager;

import org.devfleet.crest.model.CrestMarketOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by w9jds on 4/10/2016.
 */
public abstract class OrdersLoader extends BaseDataManager {

    @Inject CrestService publicCrest;
    @Inject SharedPreferences sharedPreferences;

    private Context context;

    public OrdersLoader(Context context) {
        super(context);

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    public abstract void onSellOrdersLoaded(List<MarketOrder> orders);
    public abstract void onBuyOrdersLoaded(List<MarketOrder> orders);
    public abstract void onMarginsLoaded(List<StationMargin> orders);
//    public abstract void onMarketHistoryLoaded()

    public void loadMarketOrders(long regionId, Type type) {
        loadStarted();
        incrementLoadingCount(3);

        publicCrest.getMarketOrders(regionId, type.getHref())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    List<CrestMarketOrder> orders = crestResponse.body().getItems();

                    int size = orders.size();
                    List<MarketOrder> marketOrders = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        marketOrders.add(CrestMapper.map(orders.get(i)));
                    }

                    buildSellOrders(marketOrders);
                    buildBuyOrders(marketOrders);
                    buildMargins(marketOrders);
                }
            })
            .subscribe();
    }

    private void buildSellOrders(List<MarketOrder> orders) {
        int size = orders.size();
        List<MarketOrder> sellOrders = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketOrder order = orders.get(i);
            if (!order.isBuyOrder()) {
                sellOrders.add(order);
            }
        }

        onSellOrdersLoaded(sellOrders);
        decrementLoadingCount();
        loadFinished();
    }

    public void loadSellOrders(final long regionId, final Type type) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getMarketOrders(regionId, type.getHref(), "sell")
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    List<CrestMarketOrder> orders = crestResponse.body().getItems();

                    int size = orders.size();
                    List<MarketOrder> marketOrders = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        marketOrders.add(CrestMapper.map(orders.get(i)));
                    }

                    onSellOrdersLoaded(marketOrders);

                    decrementLoadingCount();
                    loadFinished();
                }
            })
            .subscribe();
    }

    private void buildBuyOrders(List<MarketOrder> orders) {
        int size = orders.size();
        List<MarketOrder> sellOrders = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            MarketOrder order = orders.get(i);
            if (order.isBuyOrder()) {
                sellOrders.add(order);
            }
        }

        onSellOrdersLoaded(sellOrders);
        decrementLoadingCount();
        loadFinished();
    }

    public void loadBuyOrders(final long regionId, final Type type) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getMarketOrders(regionId, type.getHref(), "buy")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(Throwable::printStackTrace)
                .doOnNext(crestResponse -> {
                    if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                        List<CrestMarketOrder> orders = crestResponse.body().getItems();

                        int size = orders.size();
                        List<MarketOrder> marketOrders = new ArrayList<>(size);
                        for (int i = 0; i < size; i++) {
                            marketOrders.add(CrestMapper.map(orders.get(i)));
                        }

                        onBuyOrdersLoaded(marketOrders);

                        decrementLoadingCount();
                        loadFinished();
                    }
                })
                .subscribe();
    }

    public void loadMarginOrders(final long regionId, final Type type){
        loadStarted();
        incrementLoadingCount();

        publicCrest.getMarketOrders(regionId, type.getHref())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(Throwable::printStackTrace)
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    List<CrestMarketOrder> orders = crestResponse.body().getItems();

                    int size = orders.size();
                    List<MarketOrder> marketOrders = new ArrayList<>(size);
                    for (int i = 0; i < size; i++) {
                        marketOrders.add(CrestMapper.map(orders.get(i)));
                    }

                    buildMargins(marketOrders);
                }

            })
            .subscribe();
    }

    private void buildMargins(List<MarketOrder> orders) {
        ArrayMap<String, MarketOrder> buyMax = new ArrayMap<>();
        ArrayMap<String, MarketOrder> sellMax = new ArrayMap<>();
        int size = orders.size();

        for (int i = 0; i < size; i++) {
            MarketOrder order = orders.get(i);
            if (order.isBuyOrder()) {
                if (buyMax.containsKey(order.getLocation())) {
                    if (buyMax.get(order.getLocation()).getPrice() > order.getPrice()) {
                        continue;
                    }
                }

                buyMax.put(order.getLocation(), order);
            }
            else {
                if (sellMax.containsKey(order.getLocation())) {
                    if (sellMax.get(order.getLocation()).getPrice() > order.getPrice()) {
                        continue;
                    }
                }

                sellMax.put(order.getLocation(), order);
            }
        }

        size = sellMax.size();
        List<StationMargin> margins = new ArrayList<>(size > buyMax.size() ? size : buyMax.size());
        for (int i = 0; i < size; i++) {
            String key = sellMax.keyAt(i);
            if (buyMax.containsKey(key)) {
                MarketOrder buyOrder = buyMax.get(key);
                MarketOrder sellOrder = sellMax.get(key);

                margins.add(new StationMargin.Builder()
                    .setMaxBuyPrice(buyOrder.getPrice())
                    .setMaxSellPrice(sellOrder.getPrice())
                    .setStation(key)
                    .build());
            }
        }

        onMarginsLoaded(margins);
        decrementLoadingCount();
        loadFinished();
    }

}
