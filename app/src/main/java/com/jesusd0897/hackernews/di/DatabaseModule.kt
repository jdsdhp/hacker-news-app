package com.jesusd0897.hackernews.di

import android.content.Context
import com.jesusd0897.hackernews.data.database.AppDatabase
import com.jesusd0897.hackernews.data.database.RoomAppDatabase
import com.jesusd0897.hackernews.data.database.dao.StoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    @Singleton
    @Provides
    internal fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        RoomAppDatabase.getInstance(context)

    @Provides
    internal fun provideStoryDao(appDatabase: AppDatabase): StoryDao = appDatabase.storyDao()

}