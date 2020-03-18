package asiantech.internship.summer.retrofit.api

import asiantech.internship.summer.retrofit.model.Utils
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClientAPI {

    private var retrofit : Retrofit? = null

    fun createServiceClient(): Retrofit? {
        if (retrofit == null){
            retrofit = Retrofit.Builder()
                    .baseUrl(Utils.API_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit
    }
}
