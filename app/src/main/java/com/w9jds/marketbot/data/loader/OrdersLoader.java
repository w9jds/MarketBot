package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.w9jds.marketbot.classes.CrestMapper;
import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketHistory;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.StationMargin;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;

import org.devfleet.crest.model.CrestDictionary;
import org.devfleet.crest.model.CrestMarketHistory;
import org.devfleet.crest.model.CrestMarketOrder;
import org.devfleet.crest.model.CrestMarketType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
    public abstract void onHistoryLoaded(List<MarketHistory> historyEntries);

    public void loadMarketOrders(long regionId, Type type) {
        loadStarted();
        incrementLoadingCount(3);

        publicCrest.getMarketOrders(regionId, type.getHref())
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(exception -> Log.e("OrdersLoader", exception.getCause().getMessage()))
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
            .doOnError(exception -> Log.e("OrdersLoader", exception.getCause().getMessage()))
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

        onBuyOrdersLoaded(sellOrders);
        decrementLoadingCount();
        loadFinished();
    }

    public void loadBuyOrders(final long regionId, final Type type) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getMarketOrders(regionId, type.getHref(), "buy")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(exception -> Log.e("OrdersLoader", exception.getCause().getMessage()))
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
            .doOnError(exception -> Log.e("OrdersLoader", exception.getCause().getMessage()))
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

    public void loadMarketHistory(final long regionId, final Type type) {
        Observable.defer(() -> Observable.just(getHistoryEntries(regionId, type)))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext(entries -> {
                int size = entries.size();
                List<MarketHistory> histories = new ArrayList<MarketHistory>(size);
                for (int i = 0; i < size; i++) {
                    histories.add(CrestMapper.map(entries.get(i)));
                }

                onHistoryLoaded(histories);
                decrementLoadingCount();
                updateFinished();

            }).subscribe();
    }

    private List<CrestMarketHistory> getHistoryEntries(final long regionId, final Type type) {
        try {
            List<CrestMarketHistory> crestMarketTypes = new ArrayList<>();
            Response<CrestDictionary<CrestMarketHistory>> dictionary;
            int page = 0;

            do {
                page = page + 1;
                dictionary = publicCrest.getMarketHistory(regionId, typeId, page).execute().body();
                if (dictionary == null) {
                    break;
                }

            dictionary = publicCrest.getMarketHistory(regionId, type.getHref()).execute();
            if (!dictionary.isSuccessful() || dictionary.body() == null) {
                return crestMarketTypes;
            }

            crestMarketTypes.addAll(dictionary.body().getItems());
            return crestMarketTypes;
        }
        catch(Exception ex) {
            loadFailed("Failed to retrieve items market history");
            return new ArrayList<>();
        }
    }

}
