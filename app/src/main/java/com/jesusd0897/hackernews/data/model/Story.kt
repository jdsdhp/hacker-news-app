package com.jesusd0897.hackernews.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

const val STORY_STATUS_UNREAD = "unread"
const val STORY_STATUS_READ = "read"
const val STORY_STATUS_DELETED = "deleted"

@Entity(tableName = "stories")
@Parcelize
data class Story(
    @PrimaryKey @ColumnInfo(name = "id") @SerializedName("id") val id: Long,
    @ColumnInfo(name = "title") @SerializedName("title") val title: String,
    @ColumnInfo(name = "url") @SerializedName("url") val url: String,
    @ColumnInfo(name = "author") @SerializedName("author") val author: String,
    @ColumnInfo(name = "created_at") @SerializedName("created_at") var createdAt: Long,
    @ColumnInfo(
        name = "status",
        defaultValue = STORY_STATUS_UNREAD,
    )
    @SerializedName("status") var status: String = STORY_STATUS_UNREAD,
) : Parcelable {

    fun isUnread() = status == STORY_STATUS_UNREAD
    fun isRead() = status == STORY_STATUS_READ
    fun isDeleted() = status == STORY_STATUS_DELETED

}