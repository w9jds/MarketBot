package com.w9jds.marketbot.data.loader;

import android.content.Context;
import android.content.SharedPreferences;

import com.w9jds.marketbot.classes.CrestMapper;
import com.w9jds.marketbot.classes.CrestService;
import com.w9jds.marketbot.classes.models.MarketHistory;
import com.w9jds.marketbot.classes.models.MarketOrder;
import com.w9jds.marketbot.classes.models.Region;
import com.w9jds.marketbot.classes.models.Type;
import com.w9jds.marketbot.classes.models.TypeInfo;

import org.devfleet.crest.model.CrestType;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Response;
import rx.Observable;
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

        Observable.defer(() -> {
            try {
                return Observable.just(getTypeInfo(typeId));
            }
            catch(Exception ex) {
                return Observable.error(ex);
            }
        })
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnError(error -> {
            decrementLoadingCount();
            loadFinished();
            loadFailed(error.getMessage());
        })
        .doOnNext(crestType -> {
            onTypeInfoLoaded(CrestMapper.map(crestType));
            decrementLoadingCount();
            loadFinished();
        }).subscribe();
    }

    private CrestType getTypeInfo(long typeId) throws Exception {
        Response<CrestType> response = publicCrest.getTypeInfo(typeId).execute();
        if (!response.isSuccessful() || response.body() == null) {
            throw new Exception("Unable to load type info");
        }

        return response.body();
    }

}
