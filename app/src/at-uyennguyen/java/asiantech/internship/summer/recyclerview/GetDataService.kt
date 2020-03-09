package asiantech.internship.summer.recyclerview
import retrofit2.Call
import retrofit2.http.GET
interface GetDataService {
    @GET("/users")
    fun getUser() : Call<MutableList<User>>
    @GET("/like")
    fun getLikes() : Call<MutableList<Like>>
    @GET("/new_feeds")
    fun getNewFeeds() : Call<MutableList<NewFeed>>
}