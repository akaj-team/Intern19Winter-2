package asiantech.internship.summer.retrofit.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientAPI {

    private const val API_URL = "http://5e60c164cbbe0600146cbd19.mockapi.io/"
    private var retrofit : Retrofit? = null

    fun createServiceClient(): Retrofit? {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}
