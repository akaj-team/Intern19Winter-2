package asiantech.internship.summer.recyclerview

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface APIService {
    @GET("newfeed")
    fun getNewfeed(): Call<List<NewFeed>>

    @PUT("newfeed/{id}")
    fun updateNewFeed(@Path("id") id: Int, @Body newFeed: NewFeed): Call<NewFeed>

    @DELETE("newfeed/{id}")
    fun deleteNewFeed(@Path("id")id:Int): Call<NewFeed>

    @Multipart
    @POST("update")
    fun uploadData(
            @Part body: MultipartBody.Part
    ) : Call<RequestBody>
}
