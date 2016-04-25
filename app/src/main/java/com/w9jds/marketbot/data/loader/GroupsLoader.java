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
import org.devfleet.crest.model.CrestServerStatus;
import org.devfleet.crest.model.CrestType;

import java.util.List;

import javax.inject.Inject;

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
        incrementUpdatingCount(3);

        if (isConnected()) {
            publicCrest.getServer()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> loadFailed(error.getMessage()))
                .doOnNext(serverInfoResponse -> {
                    if (serverInfoResponse.isSuccessful() && serverInfoResponse.body() != null) {
                        CrestServerStatus serverInfo = serverInfoResponse.body();

                        if (!serverInfo.getServerVersion().equals(serverVersion) && isFirstRun) {
                            resetLoadingCount();
                            loadFinished();

                            sharedPreferences.edit()
                                    .putString("serverVersion", serverInfo.getServerVersion())
                                    .apply();



                            updateMarketGroups();
                            updateMarketTypes();
                            updateRegions();
                        }
                    }

                    updateFailed = true;
                    loadFailed("Failed to get the CREST server version");
                    updateFinished();
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

    private Action1<Response<CrestDictionary<CrestType>>> typeCallback = typesPage -> {
        if (typesPage.isSuccessful() && typesPage.body() != null) {
            CrestDictionary<CrestType> types = typesPage.body();

            MarketTypeEntry.addNewMarketTypes(types.getItems());
            if (types.getPageNext() != null && !types.getPageNext().equals("")) {
                Uri uri = Uri.parse(types.getPageNext());
                onProgressUpdate(Integer.parseInt(uri.getQueryParameter("page")), types.getPageCount());
                loadNextPageTypes(types.getPageNext());
            }
            else {
                decrementUpdatingCount();
                updateFinished();
            }
        }
        else {
            updateFailed = true;
            loadFailed("Failed to update cached market types.");
            decrementUpdatingCount();
            updateFinished();
        }
    };

    private void updateMarketTypes() {



        publicCrest.getMarketTypes();
    }

    public void loadNextPageTypes(String targetLocation) {
        publicCrest.getMarketTypes(targetLocation);
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
