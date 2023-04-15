package com.example.androidtask.di

import com.example.androidtask.BuildConfig
import com.example.androidtask.data.source.remote.Constants.TOKENENDPOINT
import com.example.androidtask.data.source.remote.EndPoints
import com.google.gson.GsonBuilder
import com.skydoves.sandwich.coroutines.CoroutinesResponseCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkRetrofitModule {
    @Singleton
    @Provides
    fun provideRetrofitBuilder(): OkHttpClient{
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(getAuthInterceptor(TOKENENDPOINT))
            .addInterceptor(logging)
            .build()
    }
    @Provides
    fun getAuthInterceptor(token : String): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val url: HttpUrl =
                original.url.newBuilder()/*.addQueryParameter(ACCESSKEY,
                  ACCESSVALUE)*/.build() // add query in request
            chain.proceed(
                original.newBuilder().url(url)
                    .header("Accept","application/json")
                    .header("private-key", token)
                    .method(original.method, original.body)
                    .build()
            )
        }
    }

    @Singleton
    @Provides
    fun provideEndPoints(client: OkHttpClient): EndPoints {
        return Retrofit.Builder()
            .client(client)
            .baseUrl(BuildConfig.baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .addCallAdapterFactory(CoroutinesResponseCallAdapterFactory())
            .build()
            .create(EndPoints::class.java)
    }

}