package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.CrestMapper;
import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.MarketHistory;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.classes.models.TypeInfo;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.storage.RegionEntry;

import org.devfleet.crest.model.CrestType;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public abstract class TypeLoader extends BaseDataManager {

    @Inject CrestService publicCrest;
    @Inject SharedPreferences sharedPreferences;

    private Context context;

    public TypeLoader(Context context) {
        super(context);

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    public abstract void onTypeInfoLoaded(TypeInfo info);
    public abstract void onRegionsLoaded(List<Region> regions);


    public void loadRegions(boolean includesWormholes) {
        onRegionsLoaded(RegionEntry.getAllRegions(includesWormholes));
    }

    public void loadTypeInfo(long typeId) {
        loadStarted();
        incrementLoadingCount();

        publicCrest.getTypeInfo(typeId)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError(error -> {
                decrementLoadingCount();
                loadFinished();
                loadFailed(error.getMessage());
            })
            .doOnNext(crestResponse -> {
                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
                    onTypeInfoLoaded(CrestMapper.map(crestResponse.body()));
                    decrementLoadingCount();
                    loadFinished();
                }
            }).subscribe();
    }

}
