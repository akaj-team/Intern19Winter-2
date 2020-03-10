package asiantech.internship.summer.recyclerView

import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface TimeLineService {
    @GET("TimeLineItem")
    fun getAll(): Call<List<TimeLineItem>>

    @PUT("TimeLineItem/{id}")
    fun updateTimeLine(@Path("id") id: Int, @Body timeLineItem: TimeLineItem): Call<TimeLineItem>

    @DELETE("TimeLineItem/{id}")
    fun deleteTimeLine(@Path("id") id: Int): Call<TimeLineItem>

    //    @Multipart
//    @POST("TimeLineItem")
//    fun addTimeLine(@Part("file\"; filename=\"pp.png\" ") name: RequestBody,
//                    @Part imageFood: MultipartBody.Part,
//                    @Part("isLike") isLike: RequestBody,
//                    @Part("like") like: RequestBody,
//                    @Part("description") description: RequestBody): Call<ResponseBody>
    @Multipart
    @POST("Image")
    fun addTimeLine(@Part filePart: MultipartBody.Part): Call<ResponseBody>
}
