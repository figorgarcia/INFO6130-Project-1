package garcia.francisco.info6130_project_1.provider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsRetrofitProvider {
    private const val BASE_URL = "https://newsapi.org/v2/"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val original = chain.request()
            val request = original.newBuilder()
                .header("User-Agent", "Mozilla/5.0 (Android 11; Mobile)")
                .build()
            chain.proceed(request)
        }
        .build()

    val retrofitInstance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client) // usa o OkHttp com User-Agent
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}