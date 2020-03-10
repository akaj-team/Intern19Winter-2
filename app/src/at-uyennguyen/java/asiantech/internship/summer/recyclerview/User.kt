package asiantech.internship.summer.recyclerview
import com.google.gson.annotations.SerializedName
class User {
    @SerializedName("id") var idUser : Int = 0
    @SerializedName("name") lateinit var nameUser : String
    @SerializedName("avatar")
    lateinit var avatar : String
}