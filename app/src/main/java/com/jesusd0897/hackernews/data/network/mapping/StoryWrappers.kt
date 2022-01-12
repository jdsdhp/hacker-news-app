package com.jesusd0897.hackernews.data.network.mapping

import com.google.gson.annotations.SerializedName

object StoryWrappers {

    data class Data(
        @SerializedName("hits") val hits: List<Hit>,
        @SerializedName("nbHits") val nbHits: Int,
        @SerializedName("page") val page: Int,
        @SerializedName("nbPages") val nbPages: Int,
        @SerializedName("hitsPerPage") val hitsPerPage: Int,
    )

    data class Hit(
        @SerializedName("story_id") val id: Long,
        @SerializedName("story_title") val storyTitle: String?,
        @SerializedName("story_url") val storyUrl: String?,
        @SerializedName("title") val title: String?,
        @SerializedName("url") val url: String?,
        @SerializedName("author") val author: String,
        @SerializedName("created_at_i") var createdAt: Long,
    )

}