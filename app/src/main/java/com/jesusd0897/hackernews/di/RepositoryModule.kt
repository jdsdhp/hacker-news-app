package com.jesusd0897.hackernews.di

import com.jesusd0897.hackernews.data.database.dao.StoryDao
import com.jesusd0897.hackernews.data.network.api.ApiService
import com.jesusd0897.hackernews.data.network.util.RequestHandler
import com.jesusd0897.hackernews.data.repository.StoryRepo
import com.jesusd0897.hackernews.data.repository.StoryRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object RepositoryModule {

    @Singleton
    @Provides
    fun provideStoryRepository(
        storyDao: StoryDao,
        service: ApiService,
        requestHandler: RequestHandler,
    ): StoryRepo = StoryRepoImpl(
        storyDao = storyDao,
        service = service,
        requestHandler = requestHandler,
    )

}