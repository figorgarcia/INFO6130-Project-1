package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityMainBinding
import garcia.francisco.info6130_project_1.databinding.FragmentTopHeadlinesBinding
import garcia.francisco.info6130_project_1.interfaces.NewsInterface
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.provider.NewsRetrofitProvider
import garcia.francisco.info6130_project_1.repository.ArticleRepository
import garcia.francisco.info6130_project_1.repository.NewsRepository
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TopHeadlinesFragment: Fragment() {

    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private lateinit var newsRepo: NewsRepository
    private lateinit var likePreferencesHelper: LikePreferencesHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        val api = NewsRetrofitProvider.retrofitInstance.create(NewsInterface::class.java)
        newsRepo = NewsRepository(api)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        likePreferencesHelper = LikePreferencesHelper(requireContext())

        adapter = ArticleAdapter(emptyList(),
            onLikeClick = { article ->
                toggleLike(article)
            },
            onArticleClick = { article ->
                val intent = ArticleDetailActivity.newIntent(requireContext(), article)
                startActivity(intent)
            }
        )

        binding.recyclerViewTopHeadlines.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopHeadlines.adapter = adapter

        loadTopHeadlines()
    }

    fun loadTopHeadlines() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = newsRepo.getHeadlines("us")
            Log.v("RESULT", response.toString())
            if (response.isSuccessful) {
                val articles = response.body()?.articles ?: emptyList()

                withContext(Dispatchers.Main) {
                    adapter.updateArticles(articles)
                    Log.d("loadTopHeadlines", "${articles.count()} headlines loaded")
                    binding.tvTitleResult2.text = "${articles.count()} headlines loaded"
                }
            } else {
                val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                withContext(Dispatchers.Main) {
                    Log.d("loadTopHeadlines", "Failed to load headlines:\n$errorMsg")
                    binding.tvTitleResult2.text = "Failed to load headlines:\n$errorMsg"
                }
            }
        }
    }

    private fun toggleLike(article: Article) {
        article.isLiked = !article.isLiked
        likePreferencesHelper.saveLikeState(article.id, article.isLiked)

        Log.d("MainActivity", "Article ${article.title} liked: ${article.isLiked}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}