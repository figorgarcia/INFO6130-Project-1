package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityLikedArticlesBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.repository.ArticleRepository

class LikedArticlesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLikedArticlesBinding
    private lateinit var adapter: ArticleAdapter
    private lateinit var likePreferencesHelper: LikePreferencesHelper
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikedArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Liked Articles"

        likePreferencesHelper = LikePreferencesHelper(this)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val likedIds: Set<String> = likePreferencesHelper.getAllLikedArticles()
        val allArticles: List<Article> = ArticleRepository.articles
        val likedArticles: List<Article> = allArticles.filter { likedIds.contains(it.id) }

        adapter = ArticleAdapter(likedArticles) { article ->
            // Optionally handle click to open detail
        }
        binding.recyclerViewLikedArticles.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewLikedArticles.adapter = adapter

        binding.tvEmptyState.visibility = if (likedArticles.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    override fun onResume() {
        super.onResume()
        // Reload liked articles
        val likedIds: Set<String> = likePreferencesHelper.getAllLikedArticles()
        val allArticles: List<Article> = ArticleRepository.articles
        val likedArticles: List<Article> = allArticles.filter { likedIds.contains(it.id) }
        adapter.updateArticles(likedArticles)
        binding.tvEmptyState.visibility = if (likedArticles.isEmpty()) android.view.View.VISIBLE else android.view.View.GONE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 