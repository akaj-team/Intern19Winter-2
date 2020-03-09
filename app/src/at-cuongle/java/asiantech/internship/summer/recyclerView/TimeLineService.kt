package asiantech.internship.summer.recyclerView

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TimeLineService {
    @GET("/TimeLineItem")
    fun getAll(): Call<List<TimeLineItem>>

    @PUT("/TimeLineItem/{id}")
    fun updateTimeLine(@Path("id") id: Int, @Body timeLineItem: TimeLineItem): Call<TimeLineItem>

    @DELETE("/TimeLineItem/{id}")
    fun deleteTimeLine(@Path("id") id: Int): Call<TimeLineItem>

    //    @Multipart
//    @POST("/TimeLineItem")
//    fun addTimeLine(@Part("id") id: Int,
//                    @Part("name") name: RequestBody,
//                    @Part imageFood: MultipartBody.Part,
//                    @Part("isLike") isLike: Boolean,
//                    @Part("like") like: Int,
//                    @Part("description") description: String): Call<ResponseBody>
    @Multipart
    @POST("/TimeLineItem")
    fun addTimeLine(
            @Part("name") name: RequestBody,
            @Part imageFood: MultipartBody.Part): Call<ResponseBody>
}
