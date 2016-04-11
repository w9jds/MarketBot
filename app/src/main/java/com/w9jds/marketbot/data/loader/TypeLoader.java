package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.MarketBot;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.classes.models.TypeInfo;
import com.w9jds.marketbot.data.BaseDataManager;
import com.w9jds.marketbot.data.storage.RegionEntry;

import javax.inject.Inject;

import core.eve.crest.CrestType;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by w9jds on 4/10/2016.
 */
public abstract class TypeLoader extends BaseDataManager {

    @Inject CrestService publicCrest;
    @Inject SharedPreferences sharedPreferences;

    private Context context;

    public TypeLoader(Context context) {
        super(context);

        MarketBot.createNewStorageSession().inject(this);
        this.context = context;
    }

    public abstract void onDataLoaded(TypeInfo info);

    public void loadRegions(boolean includesWormholes) {
//        onDataLoaded(RegionEntry.getRegions(context, includesWormholes));

        decrementLoadingCount();
        loadFinished();
    }

    public void loadTypeInfo(long typeId) {
        loadStarted();
        incrementLoadingCount();

//        publicCrest.getTypeInfo(typeId)
//            .subscribeOn(Schedulers.newThread())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnError(error -> {
//                decrementLoadingCount();
//                loadFinished();
//                loadFailed(error.getMessage());
//            })
//            .doOnNext(crestResponse -> {
//                if (crestResponse.isSuccessful() && crestResponse.body() != null) {
//                    CrestType type = crestResponse.body();
//                    onDataLoaded(type);
//
//                    decrementLoadingCount();
//                    loadFinished();
//                }
//            }).subscribe();
    }

}
