package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketItemBase;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.storage.MarketGroupEntry;
import com.w9jds.marketbot.data.storage.MarketTypeEntry;
import com.w9jds.marketbot.data.storage.RegionEntry;

import org.devfleet.crest.model.CrestDictionary;
import org.devfleet.crest.model.CrestItem;
import org.devfleet.crest.model.CrestMarketGroup;
import org.devfleet.crest.model.CrestMarketType;
import org.devfleet.crest.model.CrestServerStatus;
import org.devfleet.crest.model.CrestType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by w9jds on 4/10/2016.
 */
public abstract class GroupsLoader extends BaseDataManager {

    @Inject CrestService publicCrest;
    @Inject SharedPreferences sharedPreferences;
    @Inject boolean isFirstRun;

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
            loadMarketGroups(null);
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

    public void update() {
        updateStarted();
        incrementUpdatingCount(1);

        if (isConnected()) {
            publicCrest.getServer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> loadFailed(error.getMessage()))
                .doOnNext(serverInfoResponse -> {
                    if (serverInfoResponse.isSuccessful() && serverInfoResponse.body() != null) {
                        CrestServerStatus serverInfo = serverInfoResponse.body();
                        String serverVersion = sharedPreferences.getString("serverVersion", "");
                        if (!serverInfo.getServerVersion().equals(serverVersion) || isFirstRun) {
                            incrementUpdatingCount(3);
                            sharedPreferences.edit()
                                .putString("serverVersion", serverInfo.getServerVersion())
                                .apply();

                            updateMarketGroups();
                            updateMarketTypes(1);
                            updateRegions();
                        }
                        else {
                            decrementUpdatingCount();
                            updateFinished();
                        }
                    }
                    else {
                        updateFailed = true;
                        loadFailed("Failed to get the CREST server version");
                        updateFinished();
                    }
                }).subscribe();

        }
        else {
            updateFailed = true;
            loadFailed("Invalid Internet Connection");
            updateFinished();
        }
    }

    private void updateMarketGroups() {
        publicCrest.getMarketGroups()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(error -> {
                updateFailed = true;
                loadFailed("Failed to update cached market groups.");
                decrementUpdatingCount();
                updateFinished();
            })
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    CrestDictionary<CrestMarketGroup> marketGroups = crestResponse.body();

                    MarketGroupEntry.addNewMarketGroups(marketGroups.getItems());
                    decrementUpdatingCount();
                    updateFinished();
                }
                else {
                    updateFailed = true;
                    loadFailed("Failed to update cached market groups.");
                    decrementUpdatingCount();
                    updateFinished();
                }
            }).subscribe();
    }

    private void updateMarketTypes(int page) {
        publicCrest.getMarketTypes(page).enqueue(new Callback<CrestDictionary<CrestMarketType>>() {
            @Override
            public void onResponse(Call<CrestDictionary<CrestMarketType>> call, Response<CrestDictionary<CrestMarketType>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CrestDictionary<CrestMarketType> types = response.body();
                    MarketTypeEntry.addNewMarketTypes(types.getItems());

                    if (types.getPageNext() != null) {
                        Uri uri = Uri.parse(types.getPageNext());
                        onProgressUpdate(Integer.parseInt(uri.getQueryParameter("page")), types.getPageCount());
                        updateMarketTypes(page + 1);
                    }
                    else {
                        decrementUpdatingCount();
                        updateFinished();
                    }
                }
            }

            @Override
            public void onFailure(Call<CrestDictionary<CrestMarketType>> call, Throwable t) {
                updateFailed = true;
                decrementUpdatingCount();
                updateFinished();
            }
        });
    }

    private void updateRegions() {
        publicCrest.getRegions()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(error -> {
                updateFailed = true;
                loadFailed("Failed to update cached regions.");
                decrementUpdatingCount();
                loadFinished();
            })
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    CrestDictionary<CrestItem> regions = crestResponse.body();

                    RegionEntry.addRegions(regions.getItems());
                    decrementUpdatingCount();
                    updateFinished();
                }
                else {
                    updateFailed = true;
                    loadFailed("Failed to update cached regions.");
                    decrementUpdatingCount();
                    updateFinished();
                }
            }).subscribe();
    }

    public void loadMarketGroups(Long parentId) {
        loadStarted();
        incrementLoadingCount();
        onDataLoaded(MarketGroupEntry.getMarketGroupsForParent(parentId));
        decrementLoadingCount();
        loadFinished();
    }

    public void loadMarketTypes(long groupId) {
        loadStarted();
        incrementLoadingCount();
        onDataLoaded(MarketTypeEntry.getMarketTypes(groupId));
        decrementLoadingCount();
        loadFinished();
    }

    public void searchMarketTypes(String queryString) {
        loadStarted();
        incrementLoadingCount();
        onDataLoaded(MarketTypeEntry.searchMarketTypes(queryString));
        decrementLoadingCount();
        loadFinished();
    }
}
