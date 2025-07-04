package garcia.francisco.info6130_project_1.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityMainBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.repository.ArticleRepository
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
        setSupportActionBar(findViewById(R.id.toolbar))
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        likePreferencesHelper = LikePreferencesHelper(this)

        setupRecyclerView()
        observeViewModel()

        binding.btnLikedArticles.setOnClickListener {
            startActivity(Intent(this, LikedArticlesActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(emptyList(),
            onLikeClick = { article ->
                toggleLike(article)
            },
            onArticleClick = { article ->
                // Open detail screen on click
                val intent = ArticleDetailActivity.newIntent(this, article)
                startActivity(intent)
            })

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

                // Update the adapter with the new articles
                articleAdapter.updateArticles(articles)

                // Save to repository for sharing
                ArticleRepository.articles = articles

                // Show the RecyclerView and update status
                binding.recyclerViewArticles.visibility = View.VISIBLE
                binding.tvTitleResult.text = "${articles.size} articles loaded"
            } else {
                // Handle empty results
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

    override fun onResume() {
        super.onResume()
        // Reload like state for all articles
        val articles = viewModel._articles.value ?: return
        articles.forEach { it.isLiked = likePreferencesHelper.getLikeState(it.id) }
        articleAdapter.updateArticles(articles)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}