package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityMainBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var likePreferencesHelper: LikePreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        likePreferencesHelper = LikePreferencesHelper(this)

        setupRecyclerView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(emptyList()) { article ->
            toggleLike(article)
        }

        binding.recyclerViewArticles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }
    }

    private fun observeViewModel() {
        viewModel._articles.observe(this) { articles ->
            if (articles.isNotEmpty()) {
                // Load saved like states
                articles.forEach { article ->
                    article.isLiked = likePreferencesHelper.getLikeState(article.id)
                }

                articleAdapter.updateArticles(articles)
                binding.tvTitleResult.text = "${articles.size} articles loaded"
                binding.recyclerViewArticles.visibility = View.VISIBLE
            } else {
                binding.tvTitleResult.text = "No results found."
                binding.recyclerViewArticles.visibility = View.GONE
            }
        }
    }

    private fun toggleLike(article: Article) {
        article.isLiked = !article.isLiked
        likePreferencesHelper.saveLikeState(article.id, article.isLiked)

        Log.d("MainActivity", "Article ${article.title} liked: ${article.isLiked}")
    }

    fun onLoadClick(view: View) {
        viewModel.loadNews(1)
    }
}