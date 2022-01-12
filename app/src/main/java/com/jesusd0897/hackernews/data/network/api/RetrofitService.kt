package com.jesusd0897.hackernews.data.network.api

import com.jesusd0897.hackernews.data.network.mapping.StoryWrappers
import retrofit2.Response
import retrofit2.http.GET

internal interface RetrofitService : ApiService {

    @GET("/api/v1/search_by_date?query=mobile")
    override suspend fun fetchStories(): Response<StoryWrappers.Data>

}