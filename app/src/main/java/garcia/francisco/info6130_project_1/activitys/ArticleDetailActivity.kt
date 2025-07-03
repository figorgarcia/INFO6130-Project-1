package garcia.francisco.info6130_project_1.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import garcia.francisco.info6130_project_1.databinding.ActivityArticleDetailBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper

class ArticleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityArticleDetailBinding

    companion object {
        private const val EXTRA_ARTICLE = "extra_article"
        fun newIntent(context: Context, article: Article): Intent {
            return Intent(context, ArticleDetailActivity::class.java).apply {
                putExtra(EXTRA_ARTICLE, article)
            }
        }
    }

    private lateinit var article: Article
    private lateinit var likePreferencesHelper: LikePreferencesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Article Details"

        likePreferencesHelper = LikePreferencesHelper(this)
        article = intent.getSerializableExtra(EXTRA_ARTICLE) as Article

        binding.tvTitle.text = article.title
        binding.tvDescription.text = article.description ?: "No description"
        binding.tvContent.text = article.content ?: ""
        binding.tvPublishedAt.text = article.publishedAt

        Glide.with(this)
            .load(article.urlToImage)
            .into(binding.ivImage)

        updateLikeButton(article.isLiked)
        binding.btnLike.setOnClickListener {
            article.isLiked = !article.isLiked
            likePreferencesHelper.saveLikeState(article.id, article.isLiked)
            updateLikeButton(article.isLiked)
        }
    }

    override fun onResume() {
        super.onResume()
        // Reload like state in case it changed elsewhere
        article.isLiked = likePreferencesHelper.getLikeState(article.id)
        updateLikeButton(article.isLiked)
    }

    private fun updateLikeButton(isLiked: Boolean) {
        binding.btnLike.text = if (isLiked) "♥ Liked" else "♡ Like"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 