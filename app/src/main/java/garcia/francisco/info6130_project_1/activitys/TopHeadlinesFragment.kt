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
import garcia.francisco.info6130_project_1.provider.NewsRetrofitProvider
import garcia.francisco.info6130_project_1.repository.ArticleRepository
import garcia.francisco.info6130_project_1.repository.NewsRepository
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel
import kotlinx.coroutines.launch

class TopHeadlinesFragment: Fragment() {
    private var _binding: FragmentTopHeadlinesBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ArticleAdapter
    private lateinit var newsRepo: NewsRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        val api = NewsRetrofitProvider.retrofitInstance.create(NewsInterface::class.java)
        newsRepo = NewsRepository(api)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ArticleAdapter(
            emptyList(),
            onLikeClick = { article ->
            },
            onArticleClick = { article ->
            })

        binding.recyclerViewTopHeadlines.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewTopHeadlines.adapter = adapter

        loadTopHeadlines()
    }

    private fun loadTopHeadlines() {
        lifecycleScope.launch {
            try {
                val response = newsRepo.getHeadlines("us")  // Try "us" first
                if (response.isSuccessful) {
                    val articles = response.body()?.articles ?: emptyList()
                    adapter.updateArticles(articles)
                    Log.d("loadTopHeadlines", "${articles.count()} headlines loaded")
                    binding.tvHeadlineResult.text = "${articles.count()} headlines loaded"
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown error"
                    Log.d("loadTopHeadlines", "Failed to load headlines:\n$errorMsg")
                    binding.tvHeadlineResult.text = "Failed to load headlines:\n$errorMsg"
                }
            } catch (e: Exception) {
                Log.d("loadTopHeadlines", "Error: ${e.message}")
                binding.tvHeadlineResult.text = "Error: ${e.message}"
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}