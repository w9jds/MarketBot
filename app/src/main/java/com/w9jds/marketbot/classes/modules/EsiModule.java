package com.w9jds.marketbot.classes.modules;

import com.w9jds.marketbot.classes.EsiService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class EsiModule {

    public static final String ImageServerUri = "https://imageserver.eveonline.com/%s/%d_%d.%s";

    @Provides
    @Singleton
    Interceptor provideEsiInterceptor() {
        return chain -> {
            Request request = chain.request();

            Request.Builder builder = request.newBuilder()
                    .addHeader("User-Agent", "Marketbot-Android/v3.0.0 by Chingy Chonga/Jeremy Shore w9jds@live.com")
                    .addHeader("Accept", "application/json");

            return chain.proceed(builder.build());
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideEsiClient(Interceptor headers) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

//        if (BuildConfig.DEBUG) {
//            builder.addNetworkInterceptor(new StethoInterceptor());
//        }

        return builder.addInterceptor(headers).build();
    }

    @Provides
    @Singleton
    Retrofit provideEsiRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
            .baseUrl("https://esi.tech.ccp.is/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .client(okHttpClient)
            .build();
    }

    @Provides
    @Singleton
    EsiService provideEsiService(Retrofit retrofit) {
        return retrofit.create(EsiService.class);
    }

}
