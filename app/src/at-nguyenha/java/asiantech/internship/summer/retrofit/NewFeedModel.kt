package asiantech.internship.summer.retrofit

import com.google.gson.annotations.SerializedName

data class NewFeedModel(
        @SerializedName("id") var id: Int,
        @SerializedName("name") var name: String,
        @SerializedName("status") var status: String,
        @SerializedName("foodName") var foodName: String,
        @SerializedName("likeNumber") var likeNumber: Int,
        @SerializedName("isHeart") var isHeart: Boolean,
        @SerializedName("imageImage") var mainImage: String)
