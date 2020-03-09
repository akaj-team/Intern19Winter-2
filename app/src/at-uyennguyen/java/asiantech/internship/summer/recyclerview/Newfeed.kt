package asiantech.internship.summer.recyclerview
import com.google.gson.annotations.SerializedName
class NewFeed {
    @SerializedName("idNewFeed")
    var id : Int = 0
    @SerializedName("picture")
    lateinit var picture : String
    @SerializedName("status")
    lateinit var status : String
}