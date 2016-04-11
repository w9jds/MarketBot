package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.data.BaseDataManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

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

    public abstract void onDataLoaded(List<MarketOrder> orders);

    public void loadSellOrders(Region region, Type type) {
        loadStarted();
        incrementLoadingCount();

//        publicCrest.getOrders(region.getId(), type.getHref(), OrderType.sell, new Callback<MarketOrders>() {
//            @Override
//            public void success(MarketOrders marketOrders) {
//                if (marketOrders != null) {
//                    onDataLoaded(marketOrders.orders);
//                }
//
//                decrementLoadingCount();
//                loadFinished();
//            }
//
//            @Override
//            public void failure(String error) {
//                decrementLoadingCount();
//                loadFinished();
//            }
//        });
    }

    public void loadBuyOrders(Region region, Type type) {
        loadStarted();
        incrementLoadingCount();

//        publicCrest.getOrders(region.getId(), type.getHref(), , new Callback<MarketOrders>() {
//            @Override
//            public void success(MarketOrders marketOrders) {
//                if (marketOrders != null) {
//                    onDataLoaded(marketOrders.orders);
//                }
//
//                decrementLoadingCount();
//                loadFinished();
//            }
//
//            @Override
//            public void failure(String error) {
//
//                decrementLoadingCount();
//                loadFinished();
//            }
//        });
    }




    public void loadMarginOrders(final Region region, final Type type){
        loadStarted();
        incrementLoadingCount();

//        publicCrest.getOrders(region.getId(), type.getHref(), OrderType.buy, new Callback<MarketOrders>() {
//            @Override
//            public void success(final MarketOrders marketBuyOrders) {
//                if (marketBuyOrders != null) {
//
//                    publicCrest.getOrders(region.getId(), type.getHref(), OrderType.sell, new Callback<MarketOrders>() {
//                        @Override
//                        public void success(MarketOrders marketSellOrders) {
//                            if (marketSellOrders != null) {
//                                onDataLoaded(combineOrders(marketBuyOrders, marketSellOrders).orders);
//                            }
//                            decrementLoadingCount();
//                            loadFinished();
//                        }
//
//                        @Override
//                        public void failure(String error) {
//                            decrementLoadingCount();
//                            loadFinished();
//                        }
//                    });
//                }
//            }
//
//            @Override
//            public void failure(String error) {
//                decrementLoadingCount();
//                loadFinished();
//            }
//        });
    }

//    private MarketOrders combineOrders(MarketOrders buyOrders, MarketOrders sellOrders){
//        HashMap<String, MarketOrder> buyOrderMaximums = new HashMap<>();
//        for(MarketOrder m: buyOrders.orders){
//            if(buyOrderMaximums.containsKey(m.getLocation().getName())){
//                if(buyOrderMaximums.get(m.getLocation().getName()).getPrice() < m.getPrice()){
//                    buyOrderMaximums.put(m.getLocation().getName(), m);
//                }
//            }else{
//                buyOrderMaximums.put(m.getLocation().getName(), m);
//            }
//        }
//
//        HashMap<String, MarketOrder> sellOrderMinimums = new HashMap<>();
//        for(MarketOrder m: sellOrders.orders){
//            if(sellOrderMinimums.containsKey(m.getLocation().getName())){
//                if(sellOrderMinimums.get(m.getLocation().getName()).getPrice() > m.getPrice()){
//                    sellOrderMinimums.put(m.getLocation().getName(), m);
//                }
//            }else{
//                sellOrderMinimums.put(m.getLocation().getName(), m);
//            }
//        }
//
//        ArrayList<MarketOrder> finalOrderList = new ArrayList<>();
//
//        for(String station_name : buyOrderMaximums.keySet()){
//            if(sellOrderMinimums.containsKey(station_name)){
//                MarketOrder buyOrder = buyOrderMaximums.get(station_name);
//                MarketOrder sellOrder = sellOrderMinimums.get(station_name);
//                //(sales price - taxes and fees - purchase price) / purchase price
//                double price = (sellOrder.getPrice() - buyOrder.getPrice()) / buyOrder.getPrice();
//                buyOrder.setPrice(price);
//                buyOrder.setIsMarginOrder(true);
//                finalOrderList.add(buyOrder);
//            }
//        }
//
//        MarketOrders finalOrders = new MarketOrders();
//        finalOrders.orders = finalOrderList;
//        finalOrders.size = finalOrderList.size();
//
//        return finalOrders;
//    }

}
