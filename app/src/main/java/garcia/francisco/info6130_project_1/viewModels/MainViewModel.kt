package garcia.francisco.info6130_project_1.viewModels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import garcia.francisco.info6130_project_1.interfaces.NewsInterface
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.provider.NewsRetrofitProvider
import garcia.francisco.info6130_project_1.repository.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainViewModel() : ViewModel() {

    val _articles = MutableLiveData<List<Article>>()
    val _likes = MutableLiveData<MutableSet<String>>(mutableSetOf())

    private var currentPage = 1
    private val pageSize = 10
    private val query = "kotlin"
    private val repository: NewsRepository;

    init {
        val api = NewsRetrofitProvider.retrofitInstance.create(NewsInterface::class.java)
        repository = NewsRepository(api)
    }

    fun loadNews(page: Int = currentPage) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = repository.getNews(query, pageSize, page)
            if (response.isSuccessful) {
                _articles.postValue(response.body()?.articles ?: emptyList())
                currentPage = page
            } else {
                Log.v("API","ERROR");
            }
        }
    }

    fun likeArticle(article: Article) {
    }

    fun unlikeArticle(article: Article) {
    }

    fun loadNextPage() = loadNews(currentPage + 1)
    fun loadPreviousPage() = if (currentPage > 1) loadNews(currentPage - 1) else null;
}
