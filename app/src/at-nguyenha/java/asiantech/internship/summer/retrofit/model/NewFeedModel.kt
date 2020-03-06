package asiantech.internship.summer.retrofit.model

import com.google.gson.annotations.SerializedName

data class NewFeedModel(
        @SerializedName("id") var id: Int = 0,
        @SerializedName("name") var name: String,
        @SerializedName("status") var status: String,
        @SerializedName("foodName") var foodName: String,
        @SerializedName("likeNumber") var likeNumber: Int,
        @SerializedName("isHeart") var isHeart: Boolean,
        @SerializedName("mainImage") var mainImage: String)
