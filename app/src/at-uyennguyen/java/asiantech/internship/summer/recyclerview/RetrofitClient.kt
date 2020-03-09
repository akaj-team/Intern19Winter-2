package asiantech.internship.summer.recyclerview
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitClient {
    companion object {
        var retrofit: Retrofit ?= null
        private var API_URL = "http://5e64a4dca49c210016106ae5.mockapi.io/"
        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                        .baseUrl(API_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            }
            return retrofit
        }
    }
}