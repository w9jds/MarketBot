//package com.w9jds.marketbot.data.loader;
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.support.annotation.Nullable;
//
//import com.w9jds.marketbot.classes.models.MarketItemBase;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.inject.Inject;
//
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.schedulers.Schedulers;
//
//public abstract class GroupsLoader extends BaseDataManager {
//
//    @Inject
//    EsiService esiService;
//    @Inject
//    SharedPreferences sharedPreferences;
//    @Inject boolean isFirstRun;
//
//    private Context context;
//    private boolean updateFailed = false;
//
//    public abstract void onProgressUpdate(int page, int totalPages, @Nullable String message);
//    public abstract void onDataLoaded(List<? extends MarketItemBase> data);
//
//    public GroupsLoader(Context context) {
//        super(context);
//
//        MarketBot.createNewStorageSession().inject(this);
//        this.context = context;
//    }
//
//    private void incrementUpdatingCount(int count) {
//        updatingCount.set(count);
//    }
//
//    private void decrementUpdatingCount() {
//        updatingCount.decrementAndGet();
//    }
//
//    private int updatingCount() {
//        return updatingCount.intValue();
//    }
//
//    private void updateStarted() {
//        dispatchUpdateStartedCallbacks();
//    }
//
//
//    @Override
//    protected void updateFinished() {
//        super.updateFinished();
//
//        if (!updateFailed && isFirstRun && updatingCount() == 0) {
//            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
//            loadMarketGroups(null);
//        }
//    }
//
//    private void dispatchUpdateStartedCallbacks() {
//        if (updatingCount.intValue() == 0) {
//            if (updatingCallbacks != null && !updatingCallbacks.isEmpty()) {
//                for (DataUpdatingCallbacks updatingCallback : updatingCallbacks) {
//                    updatingCallback.dataUpdatingStarted();
//                }
//            }
//        }
//    }
//
//    public void update() {
//        updateStarted();
//        incrementUpdatingCount(1);
//
//        if (isConnected()) {
//
//            publicCrest.getServer()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnError(error -> loadFailed(error.getMessage()))
//                .doOnNext(serverInfoResponse -> {
//                    if (serverInfoResponse.isSuccessful() && serverInfoResponse.body() != null) {
//                        CrestServerStatus serverInfo = serverInfoResponse.body();
//                        String serverVersion = sharedPreferences.getString("serverVersion", "");
//                        if (!serverInfo.getServerVersion().equals(serverVersion) || isFirstRun) {
//                            incrementUpdatingCount(3);
//                            sharedPreferences.edit()
//                                .putString("serverVersion", serverInfo.getServerVersion())
//                                .apply();
//
//                            updateMarketGroups();
//                            updateMarketTypes();
//                            updateRegions();
//                        }
//                        else {
//                            decrementUpdatingCount();
//                            updateFinished();
//                        }
//                    }
//                    else {
//                        updateFailed = true;
//                        loadFailed("Failed to get the CREST server version");
//                        updateFinished();
//                    }
//                }).subscribe();
//
//        }
//        else {
//            updateFailed = true;
//            loadFailed("Invalid Internet Connection");
//            updateFinished();
//        }
//    }
//
//    private void updateMarketGroups() {
//        publicCrest.getMarketGroups()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError(error -> {
//                updateFailed = true;
//                loadFailed("Failed to update cached market groups.");
//                decrementUpdatingCount();
//                updateFinished();
//            })
//            .doOnNext(crestResponse -> {
//                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
//                    CrestDictionary<CrestMarketGroup> marketGroups = crestResponse.body();
//
//                    MarketGroupEntry.addNewMarketGroups(marketGroups.getItems());
//                    decrementUpdatingCount();
//                    updateFinished();
//                }
//                else {
//                    updateFailed = true;
//                    loadFailed("Failed to update cached market groups.");
//                    decrementUpdatingCount();
//                    updateFinished();
//                }
//            }).subscribe();
//    }
//
//    private List<CrestMarketType> getAllMarketTypes() {
//        try {
//            List<CrestMarketType> crestMarketTypes = new ArrayList<>();
//            CrestDictionary<CrestMarketType> dictionary;
//            int page = 0;
//
//            do {
//                page = page + 1;
//                dictionary = publicCrest.getMarketTypes(page).execute().body();
//                if (dictionary == null) {
//                    break;
//                }
//
//                crestMarketTypes.addAll(dictionary.getItems());
//                onProgressUpdate(crestMarketTypes.size(), (int)dictionary.getTotalCount(), null);
//            } while(dictionary.getPageNext() != null);
//
//            return crestMarketTypes;
//        }
//        catch(Exception ex) {
//            updateFailed = true;
//            decrementUpdatingCount();
//            updateFinished();
//
//            return new ArrayList<>();
//        }
//    }
//
//    private void updateMarketTypes() {
//        BehaviorSubject<Map.Entry<Integer, Integer>> subject = BehaviorSubject.create();
//        CompositeSubscription subscriptions = new CompositeSubscription();
//        subscriptions.add(subject
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnNext(progress -> {
//                onProgressUpdate(progress.getKey(), progress.getValue(), "Storing items list...");
//
//                if (progress.getKey().equals(progress.getValue())) {
//                    subscriptions.unsubscribe();
//                    decrementUpdatingCount();
//                    updateFinished();
//                }
//            }).subscribe());
//
//
//        Observable.defer(() -> Observable.just(getAllMarketTypes()))
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnNext(orders -> MarketTypeEntry.addNewMarketTypes(orders, subject))
//            .subscribe();
//    }
//
//    private void updateRegions() {
//        publicCrest.getRegions()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError(error -> {
//                updateFailed = true;
//                loadFailed("Failed to update cached regions.");
//                decrementUpdatingCount();
//                loadFinished();
//            })
//            .doOnNext(crestResponse -> {
//                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
//                    CrestDictionary<CrestItem> regions = crestResponse.body();
//
//                    RegionEntry.addRegions(regions.getItems());
//                    decrementUpdatingCount();
//                    updateFinished();
//                }
//                else {
//                    updateFailed = true;
//                    loadFailed("Failed to update cached regions.");
//                    decrementUpdatingCount();
//                    updateFinished();
//                }
//            }).subscribe();
//    }
//
//    public void loadMarketGroups(Long parentId) {
//        loadStarted();
//        incrementLoadingCount();
//        onDataLoaded(MarketGroupEntry.getMarketGroupsForParent(parentId));
//        decrementLoadingCount();
//        loadFinished();
//    }
//
//    public void loadMarketTypes(long groupId) {
//        loadStarted();
//        incrementLoadingCount();
//        onDataLoaded(MarketTypeEntry.getMarketTypes(groupId));
//        decrementLoadingCount();
//        loadFinished();
//    }
//
//    public void searchMarketTypes(String queryString) {
//        loadStarted();
//        incrementLoadingCount();
//        onDataLoaded(MarketTypeEntry.searchMarketTypes(queryString));
//        decrementLoadingCount();
//        loadFinished();
//    }
//}
