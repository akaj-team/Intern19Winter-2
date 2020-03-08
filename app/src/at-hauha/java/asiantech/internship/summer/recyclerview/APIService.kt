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
    @POST("newfeed")
    fun uploadData(
            @Part("name") name : String,
            @Part("picture") picture : MultipartBody.Part,
            @Part("isStatus") isStatus : Boolean,
            @Part("like") like : Int,
            @Part("foodNAme")foodName : String,
            @Part("preview")preview : String
    ) : Call<NewFeed>
}