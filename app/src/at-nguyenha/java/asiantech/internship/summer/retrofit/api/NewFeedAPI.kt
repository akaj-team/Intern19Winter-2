package asiantech.internship.summer.retrofit.api

import asiantech.internship.summer.retrofit.model.NewFeedModel
import retrofit2.Call
import retrofit2.http.*

interface NewFeedAPI {
    @GET("retrofit/newfeed")
    fun getAllNewFeed() : Call<MutableList<NewFeedModel>>

    @POST("retrofit/newfeed")
    fun addNewFeed(@Body newFeedModel: NewFeedModel) : Call<NewFeedModel>

    @PUT("retrofit/newfeed/{id}")
    fun updateNewFeed(@Path("id") id : Int, @Body newFeed : NewFeedModel) : Call<NewFeedModel>

    @DELETE("retrofit/newfeed/{id}")
    fun deleteNewFeed(@Path("id") id: Int) : Call<NewFeedModel>
}
