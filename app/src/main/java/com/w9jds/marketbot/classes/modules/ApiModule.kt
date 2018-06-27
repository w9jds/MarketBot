package com.w9jds.marketbot.classes.modules

import com.w9jds.marketbot.classes.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideApiInterceptor(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()

            val builder = request.newBuilder()
                    .addHeader("User-Agent", "Marketbot-Android/v4.0.0 by Chingy Chonga/Jeremy Shore w9jds@live.com")
                    .addHeader("Accept", "application/json")

            chain.proceed(builder.build())
        }
    }

    @Provides
    @Singleton
    fun provideApiClient(headers: Interceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()

//        if (BuildConfig.DEBUG) {
//            builder.addNetworkInterceptor(StethoInterceptor())
//        }

        return builder.addInterceptor(headers).build()
    }

    @Provides
    @Singleton
    fun provideApiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://esi.evetech.net/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}