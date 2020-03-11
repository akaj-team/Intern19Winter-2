package asiantech.internship.summer.recyclerview
import retrofit2.Call
import retrofit2.http.*

interface NewFeedAPI {
    @GET("/new_feeds")
    fun getNewFeeds() : Call<MutableList<NewFeed>>
    @PUT("/new_feeds/{id}")
    fun updateNewFeed(@Path("id") id : Int, @Body newFeed : NewFeed) : Call<NewFeed>
    @DELETE("/new_feeds/{id}")
    fun deleteNewFeed(@Path("id") id : Int): Call<NewFeed>
    @POST("/new_feeds")
    fun addNewFeed(@Body newFeed: NewFeed) : Call<NewFeed>
}
