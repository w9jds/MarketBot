package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;

import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketItemBase;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.DataLoadingSubject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import core.eve.crest.CrestDictionary;
import core.eve.crest.CrestServerStatus;
import core.eve.crest.CrestType;
import retrofit2.Response;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by w9jds on 4/10/2016.
 */
public abstract class GroupsLoader extends BaseDataManager {

    @Inject CrestService publicCrest;
    @Inject SharedPreferences sharedPreferences;
    @Inject boolean isFirstRun;
    @Inject String serverVersion;

    private Context context;
    private boolean updateFailed = false;

    public abstract void onProgressUpdate(int page, int totalPages);
    public abstract void onDataLoaded(List<? extends MarketItemBase> data);

    public GroupsLoader(Context context) {
        super(context);

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    private void incrementUpdatingCount(int count) {
        updatingCount.set(count);
    }

    private void decrementUpdatingCount() {
        updatingCount.decrementAndGet();
    }

    private int updatingCount() {
        return updatingCount.intValue();
    }

    private void updateStarted() {
        dispatchUpdateStartedCallbacks();
    }


    @Override
    protected void updateFinished() {
        super.updateFinished();

        if (!updateFailed && isFirstRun && updatingCount() == 0) {
            sharedPreferences.edit().putBoolean("isFirstRun", false).apply();
            loadMarketGroups(null, false);
        }
    }

    private void dispatchUpdateStartedCallbacks() {
        if (updatingCount.intValue() == 0) {
            if (updatingCallbacks != null && !updatingCallbacks.isEmpty()) {
                for (DataUpdatingCallbacks updatingCallback : updatingCallbacks) {
                    updatingCallback.dataUpdatingStarted();
                }
            }
        }
    }

    public void updateAndLoad() {
        loadStarted();
        incrementLoadingCount();

        if (isConnected()) {

            publicCrest.getServer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> loadFailed(error.getMessage()))
                .doOnNext(serverInfoResponse -> {
                    if (serverInfoResponse.isSuccessful() && serverInfoResponse.body() != null) {
                        CrestServerStatus serverInfo = serverInfoResponse.body();

                        if (serverInfo.getServerVersion().equals(serverVersion) && !isFirstRun) {
                            loadMarketGroups(null, false);
                        }
                        else {
                            resetLoadingCount();
                            loadFinished();

                            sharedPreferences.edit()
                                    .putString("serverVersion", serverInfo.getServerVersion())
                                    .apply();

                            updateStarted();
                            incrementUpdatingCount(3);

                            updateMarketGroups();
                            updateMarketTypes();
                            updateRegions();
                        }
                    }
                    else {
                        loadMarketGroups(null, false);
                    }

                    //TODO if this fails I need to tell the user

                }).subscribe();

        }
        else {
            loadMarketGroups(null, false);
        }
    }



    private void updateMarketGroups() {
//        publicCrest.getMarketGroups()
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError(error -> {
//                updateFailed = true;
//                loadFailed("Failed to update cached market groups.");
//                decrementUpdatingCount();
//                updateFinished(updateDatabase);
//            })
//            .doOnNext(crestResponse -> {
//                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
//                    CrestDictionary<CrestMarketGroup> marketGroups = crestResponse.body();
//
//                    createNewMarketGroups(marketGroups.getItems());
//                    decrementUpdatingCount();
//                    updateFinished(updateDatabase);
//                }
//                else {
//                    updateFailed = true;
//                    loadFailed("Failed to update cached market groups.");
//                    decrementUpdatingCount();
//                    updateFinished(updateDatabase);
//                }
//            }).subscribe();
    }

    private Action1<Response<CrestDictionary<CrestType>>> typeCallback = typesPage -> {
//        if (typesPage.isSuccessful() && typesPage.body() != null) {
//            CrestDictionary<CrestType> types = typesPage.body();
//
//            createNewMarketTypes(types.getItems());
//            if (types.getPageNext() != null && !types.getPageNext().equals("")) {
//                Uri uri = Uri.parse(types.getPageNext());
//                onProgressUpdate(Integer.parseInt(uri.getQueryParameter("page")),
//                        types.getPageCount());
//
//                loadNextPageTypes(types.getPageNext());
//            }
//            else {
//                decrementUpdatingCount();
//                updateFinished(updateDatabase);
//            }
//        }
//        else {
//            updateFailed = true;
//            loadFailed("Failed to update cached market types.");
//            decrementUpdatingCount();
//            updateFinished(updateDatabase);
//        }
    };

    private void updateMarketTypes() {
//        publicCrest.getAllMarketTypes(marketTypeCallback);
    }

    public void loadNextPageTypes(String targetLocation) {
//        publicCrest.getMarketTypes(targetLocation, marketTypeCallback);
    }

    private void updateRegions() {
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
//                    createNewRegions(regions.getItems());
//                    decrementUpdatingCount();
//                    updateFinished(updateDatabase);
//                }
//                else {
//                    updateFailed = true;
//                    loadFailed("Failed to update cached regions.");
//                    decrementUpdatingCount();
//                    loadFinished();
//                }
//            }).subscribe();
    }

    public void loadMarketGroups(Long parentId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
            incrementLoadingCount();
        }

//        onDataLoaded(MarketGroupEntry.getMarketGroupsforParent(context, parentId));
        decrementLoadingCount();
        loadFinished();
    }

    public void loadMarketTypes(long groupId, boolean isDirectCall) {
        if (isDirectCall) {
            loadStarted();
            incrementLoadingCount();
        }

//        onDataLoaded(MarketTypeEntry.getMarketTypes(context, groupId));
        decrementLoadingCount();
        loadFinished();
    }


    public void searchMarketTypes(String queryString) {
        loadStarted();
        incrementLoadingCount();

//        onDataLoaded(MarketTypeEntry.searchMarketTypes(context, queryString));
        decrementLoadingCount();
        loadFinished();
    }

}
