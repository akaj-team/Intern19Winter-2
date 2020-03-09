package asiantech.internship.summer.recyclerView

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object TimeLineAPI {

    private const val TIME_LINE_URL = "http://5e60bf5dcbbe0600146cbce0.mockapi.io"
    private var retrofit: Retrofit? = null

    fun newInstance(): Retrofit? {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl(TIME_LINE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }
        return retrofit

    }
}
