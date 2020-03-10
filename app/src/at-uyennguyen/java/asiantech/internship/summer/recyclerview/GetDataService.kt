package asiantech.internship.summer.recyclerview
import retrofit2.Call
import retrofit2.http.*

interface GetDataService {
    @GET("/new_feeds")
    fun getNewFeeds() : Call<MutableList<NewFeed>>
    @PUT("/new_feeds/{id}")
    fun updateNewFeed(@Path("id") id : Int, @Body newFeed : NewFeed) : Call<NewFeed>
    @DELETE("/new_feed/{id}")
    fun deleteNewFeed(@Path("id") id : Int): Call<NewFeed>
}