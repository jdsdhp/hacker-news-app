package com.jesusd0897.hackernews.data.database.dao

import androidx.room.*
import com.jesusd0897.hackernews.data.model.Story
import kotlinx.coroutines.flow.Flow

@Dao
interface StoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(stories: List<Story>)

    @Update
    suspend fun update(story: Story)

    @Query("DELETE FROM stories")
    suspend fun clean()

    @Query("SELECT * FROM stories WHERE status LIKE 'read' OR status LIKE 'unread' ORDER BY created_at DESC")
    fun all(): Flow<List<Story>>

}