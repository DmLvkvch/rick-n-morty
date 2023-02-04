package com.example.ricknmortyapp.di.modules

import com.example.data.network.CharacterApiService
import com.example.data.network.EpisodeApiService
import com.example.data.network.LocationApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RestModule {

    @Singleton
    @Provides
    fun provideCharacterApi(retrofit: Retrofit): CharacterApiService =
        retrofit.create(CharacterApiService::class.java)

    @Singleton
    @Provides
    fun provideLocationApi(retrofit: Retrofit): LocationApiService =
        retrofit.create(LocationApiService::class.java)

    @Singleton
    @Provides
    fun provideEpisodeApi(retrofit: Retrofit): EpisodeApiService =
        retrofit.create(EpisodeApiService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(API_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideLogger() = HttpLoggingInterceptor().setLevel(
        HttpLoggingInterceptor.Level.BODY
    )

    @Singleton
    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    companion object {
        private const val API_URL = "https://rickandmortyapi.com/api/"
    }
}