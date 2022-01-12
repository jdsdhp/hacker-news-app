package com.jesusd0897.hackernews.data.repository

import com.jesusd0897.hackernews.data.database.dao.StoryDao
import com.jesusd0897.hackernews.data.model.STORY_STATUS_DELETED
import com.jesusd0897.hackernews.data.model.Story
import com.jesusd0897.hackernews.data.network.api.ApiService
import com.jesusd0897.hackernews.data.network.mapping.StoryWrappers
import com.jesusd0897.hackernews.data.network.util.RequestHandler
import com.jesusd0897.hackernews.data.network.util.ResponseResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

internal class StoryRepoImpl internal constructor(
    private val storyDao: StoryDao,
    private val service: ApiService,
    private val requestHandler: RequestHandler,
) : StoryRepo {

    override fun all(): Flow<List<Story>> = storyDao.all()

    override suspend fun fetchStories(): ResponseResult<StoryWrappers.Data> =
        withContext(Dispatchers.IO) {
            when (val responseResult = requestHandler.safeApiCall { service.fetchStories() }) {
                is ResponseResult.Success -> {
                    val hits: List<StoryWrappers.Hit>? = responseResult.data?.hits
                    if (!hits.isNullOrEmpty()) {
                        val stories = hits.map {
                            Story(
                                id = it.id,
                                title = it.title ?: it.storyTitle ?: "No Title",
                                url = it.url ?: it.storyUrl ?: "No URL",
                                author = it.author,
                                createdAt = it.createdAt,
                            )
                        }
                        storyDao.insert(stories)
                    }
                    return@withContext responseResult
                }
                else -> return@withContext responseResult
            }
        }

    override suspend fun deleteStory(story: Story) {
        storyDao.update(story.apply { status = STORY_STATUS_DELETED })
    }

}
