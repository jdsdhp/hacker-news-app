package com.jesusd0897.hackernews.data.network.api

import com.jesusd0897.hackernews.data.network.mapping.StoryWrappers
import retrofit2.Response

internal interface ApiService {

    suspend fun fetchStories(): Response<StoryWrappers.Data>

}