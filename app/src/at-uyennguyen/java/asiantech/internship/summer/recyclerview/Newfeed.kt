package asiantech.internship.summer.recyclerview
import com.google.gson.annotations.SerializedName
class NewFeed {
    @SerializedName("id")
    var id : Int = 0
    @SerializedName("name_user")
    lateinit var name : String
    @SerializedName("avatar")
    lateinit var avatar : String
    @SerializedName("picture")
    lateinit var picture : String
    @SerializedName("status")
    lateinit var status : String
    @SerializedName("number_like")
    var numberLike : Int = 0
    @SerializedName("is_like")
    var isLike : Boolean = false
}