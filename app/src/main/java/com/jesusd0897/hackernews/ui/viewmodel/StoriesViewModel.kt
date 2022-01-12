package com.jesusd0897.hackernews.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jesusd0897.hackernews.data.model.Story
import com.jesusd0897.hackernews.data.network.util.ResponseResult
import com.jesusd0897.hackernews.data.repository.StoryRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class StoriesViewModel @Inject constructor(private val storyRepo: StoryRepo) :
    BasicViewModel() {

    internal val stories: LiveData<List<Story>> = storyRepo.all().asLiveData()

    init {
        fetchStories()
    }

    fun fetchStories() = viewModelScope.launch {
        when (val response = storyRepo.fetchStories()) {
            is ResponseResult.Loading -> setLoadingState(true)
            is ResponseResult.Success -> setLoadingState(false)
            is ResponseResult.Error -> {
                setLoadingState(false)
                setErrorMessage(response.exception.second)
            }
        }
    }

    fun deleteStory(story: Story) = viewModelScope.launch { storyRepo.deleteStory(story) }

}