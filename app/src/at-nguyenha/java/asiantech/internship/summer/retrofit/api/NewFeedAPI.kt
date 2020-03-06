package asiantech.internship.summer.retrofit.api

import asiantech.internship.summer.retrofit.NewFeedModel
import retrofit2.Call
import retrofit2.http.GET

interface NewFeedAPI {
    @GET("retrofit/newfeed")
    fun getAllNewFeed() : Call<MutableList<NewFeedModel>>

}