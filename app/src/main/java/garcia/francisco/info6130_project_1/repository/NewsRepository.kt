package garcia.francisco.info6130_project_1.repository

import android.util.Log
import garcia.francisco.info6130_project_1.interfaces.NewsInterface
import garcia.francisco.info6130_project_1.models.NewsResponse
import retrofit2.Response

class NewsRepository(private val api: NewsInterface) {
    private val apiKey = "002a220aea45414a8000d5fb4879b943"

    suspend fun getNews(query: String, pageSize: Int, page: Int): Response<NewsResponse> {
        return api.getNews(query, pageSize, page, apiKey)
    }

    suspend fun getHeadlines(country: String): Response<NewsResponse> {
        return  api.getHeadlines(country, apiKey)
    }

}