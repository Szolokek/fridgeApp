package com.example.fridge.di

import android.content.Context
import com.example.fridge.data.FridgeDao
import com.example.fridge.data.FridgeDatabase
import com.example.fridge.data.FridgeRepository
import com.example.fridge.data.FridgeRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFridgeRepository(@ApplicationContext appContext: Context): FridgeRepository {
        return FridgeRepositoryImpl(FridgeDatabase.getDatabase(appContext).fridgeDao())
    }

    @Provides
    @Singleton
    fun provideFridgeDao(@ApplicationContext appContext: Context): FridgeDao {
        return FridgeDatabase.getDatabase(appContext).fridgeDao()
    }
}