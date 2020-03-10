package asiantech.internship.summer.recyclerView

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TimeLineAPI {

    private const val TIME_LINE_URL = "http://5e60bf5dcbbe0600146cbce0.mockapi.io/v1/"
    private var retrofit: Retrofit? = null
    private fun provideOkHttpClient(): OkHttpClient = OkHttpClient
            .Builder()
            .build()

//            .connectTimeout(10, TimeUnit.SECONDS)
//            .writeTimeout(60, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)

    fun newInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(TIME_LINE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(provideOkHttpClient())
                    .build()
        }
        return retrofit

    }
}
