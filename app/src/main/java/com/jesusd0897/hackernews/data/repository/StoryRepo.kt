package com.jesusd0897.hackernews.data.repository

import com.jesusd0897.hackernews.data.model.Story
import com.jesusd0897.hackernews.data.network.mapping.StoryWrappers
import com.jesusd0897.hackernews.data.network.util.ResponseResult
import kotlinx.coroutines.flow.Flow

internal interface StoryRepo {

    fun all(): Flow<List<Story>>

    suspend fun fetchStories(): ResponseResult<StoryWrappers.Data>

    suspend fun deleteStory(story: Story)

}
