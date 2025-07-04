package garcia.francisco.info6130_project_1.interfaces

import garcia.francisco.info6130_project_1.models.NewsResponse
import garcia.francisco.info6130_project_1.models.SourcesResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

    @GET("sources")
    fun getSources(
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ): Call<SourcesResponse>
}