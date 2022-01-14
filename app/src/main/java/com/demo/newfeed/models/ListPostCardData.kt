package com.demo.newfeed.models

import com.google.gson.annotations.SerializedName

data class ListPostCardData(
    @SerializedName("data")
    val data: List<PostCardData>?
)

data class PostCardData(
    @SerializedName("name")
    val name: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("body")
    val body: String?,
    @SerializedName("background_type")
    val background_type: Int?,
    @SerializedName("background_url")
    val background_url: String?,
    @SerializedName("liked")
    val liked: Boolean?,
    @SerializedName("like_count")
    val like_count: Long?,
    @SerializedName("viewed")
    val viewed: Boolean?,
    @SerializedName("view_count")
    val view_count: Long?,
    @SerializedName("shared")
    val shared: Boolean?,
    @SerializedName("share_count")
    val share_count: Long?,
    @SerializedName("timestamp")
    val timestamp: String?,
    @SerializedName("color")
    val color: color?

)

data class color(
    @SerializedName("bar")
    val bar: bar
)

data class bar(
    @SerializedName("top")
    val top: String?,
    @SerializedName("bottom")
    val bottom: String?
)