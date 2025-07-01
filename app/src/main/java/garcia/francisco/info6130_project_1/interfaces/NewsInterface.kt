package garcia.francisco.info6130_project_1.interfaces

import garcia.francisco.info6130_project_1.models.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET("v2/everything")
    suspend fun getNews(
        @Query("q") query: String,
        @Query("pageSize") pageSize: Int,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>
}