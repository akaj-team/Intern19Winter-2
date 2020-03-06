package asiantech.internship.summer.recyclerview

import com.google.gson.annotations.SerializedName

class NewFeed {
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("name")
    var name: String = ""

    @SerializedName("picture")
    var picture: String = ""

    @SerializedName("isStatus")
    var isStatus: Boolean = false

    @SerializedName("like")
    var like: Int = 0

    @SerializedName("food_name")
    var foodName: String = ""

    @SerializedName("preview")
    var preview: String = ""
}
