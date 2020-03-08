package asiantech.internship.summer.recyclerview

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIRetrofit {
    private var retrofit: Retrofit? = null
    private const val BASE_URL = "http://5e60cfb2cbbe0600146cbdc2.mockapi.io/"

    fun getRetrofitInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}