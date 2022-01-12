package com.jesusd0897.hackernews.data.database

import com.jesusd0897.hackernews.data.database.dao.StoryDao

internal interface AppDatabase {

    fun storyDao(): StoryDao

}