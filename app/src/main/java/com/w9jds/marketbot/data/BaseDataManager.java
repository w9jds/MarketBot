package com.w9jds.marketbot.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import rx.subscriptions.CompositeSubscription;

public abstract class BaseDataManager implements DataLoadingSubject {

    private Context context;
    private CompositeSubscription subscriptions;

    private AtomicInteger loadingCount;
    private List<DataLoadingCallbacks> loadingCallbacks;

    protected AtomicInteger updatingCount;
    protected List<DataUpdatingCallbacks> updatingCallbacks;

    public BaseDataManager(Context context) {
        this.context = context;
        this.subscriptions = new CompositeSubscription();

        loadingCount = new AtomicInteger(0);
        updatingCount = new AtomicInteger(0);
    }

    @Override
    public boolean isDataLoading() {
        return loadingCount.get() > 0;
    }

    protected void loadStarted() {
        dispatchLoadingStartedCallbacks();
    }

    protected void loadFinished() {
        dispatchLoadingFinishedCallbacks();
    }

    protected  void loadFailed(String errorMessage) {
        dispatchLoadingFailedCallbacks(errorMessage);
    }

    protected void resetLoadingCount() {
        loadingCount.set(0);
    }

    protected void incrementLoadingCount() {
        loadingCount.incrementAndGet();
    }

    protected void incrementLoadingCount(int count) {
        loadingCount.set(count);
    }

    protected void decrementLoadingCount() {
        loadingCount.decrementAndGet();
    }

    protected void updateFinished() {
        dispatchUpdateFinishedCallbacks();
    }

    protected boolean isConnected() {
        ConnectivityManager manager = (ConnectivityManager)context.getSystemService(
                Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    @Override
    public void registerLoadingCallback(DataLoadingSubject.DataLoadingCallbacks callback) {
        if (loadingCallbacks == null) {
            loadingCallbacks = new ArrayList<>(1);
        }

        loadingCallbacks.add(callback);
    }

    @Override
    public void registerUpdatingCallback(DataLoadingSubject.DataUpdatingCallbacks callback) {
        if (updatingCallbacks == null) {
            updatingCallbacks = new ArrayList<>(1);
        }

        updatingCallbacks.add(callback);
    }

    @Override
    public void unregisterUpdatingCallback(DataLoadingSubject.DataUpdatingCallbacks callback) {
        if (updatingCallbacks.contains(callback)) {
            updatingCallbacks.remove(callback);
        }
    }

    @Override
    public void unregisterLoadingCallback(DataLoadingSubject.DataLoadingCallbacks callback) {
        if (loadingCallbacks.contains(callback)) {
            loadingCallbacks.remove(callback);
        }
    }

    protected void dispatchLoadingStartedCallbacks() {
        if (loadingCount.intValue() == 0) {
            if (loadingCallbacks != null && !loadingCallbacks.isEmpty()) {
                for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
                    loadingCallback.dataStartedLoading();
                }
            }
        }
    }

    protected void dispatchLoadingFinishedCallbacks() {
        if (loadingCount.intValue() == 0) {
            if (loadingCallbacks != null && !loadingCallbacks.isEmpty()) {
                for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
                    loadingCallback.dataFinishedLoading();
                }
            }
        }
    }

    protected void dispatchLoadingFailedCallbacks(String errorMessage) {
        if (loadingCallbacks != null && !loadingCallbacks.isEmpty()) {
            for (DataLoadingCallbacks loadingCallback : loadingCallbacks) {
                loadingCallback.dataFailedLoading(errorMessage);
            }
        }
    }

    protected void dispatchUpdateFinishedCallbacks() {
        if (updatingCount.intValue() == 0) {
            if (updatingCallbacks != null && !updatingCallbacks.isEmpty()) {
                for (DataUpdatingCallbacks updatingCallback : updatingCallbacks) {
                    updatingCallback.dataUpdatingFinished();
                }
            }
        }
    }

}