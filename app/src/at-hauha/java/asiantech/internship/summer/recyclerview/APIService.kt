package asiantech.internship.summer.recyclerview

import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("newfeed")
    fun getNewfeed(): Call<List<NewFeed>>
}