package asiantech.internship.summer.recyclerView

import com.google.gson.annotations.SerializedName

class TimeLineItem(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("name")
        var name: String = "",
        @SerializedName("imageFood")
        var imageFood: String = "",
        @SerializedName("isLike")
        var isLike: Boolean = false,
        @SerializedName("like")
        var like: Int = 0,
        @SerializedName("description")
        var description: String = ""
)
//{"id":"1",
//    "name":"name 1",
//    "imageFood":"http://lorempixel.com/640/480/business",
//    "isLike":false,
//    "like":40,
//    "description":"description 1"
//}