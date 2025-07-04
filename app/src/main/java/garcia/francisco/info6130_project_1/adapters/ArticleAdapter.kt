package garcia.francisco.info6130_project_1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.models.Article

class ArticleAdapter(
    private var articles: List<Article>,
    private val onLikeClick: (Article) -> Unit,
    private val onArticleClick: (Article) -> Unit
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivArticleImage)
        val titleTextView: TextView = itemView.findViewById(R.id.tvArticleTitle)
        val descriptionTextView: TextView = itemView.findViewById(R.id.tvArticleDescription)
        val likeTextView: TextView = itemView.findViewById(R.id.tvLikeState)
        val likeButton: Button = itemView.findViewById(R.id.btnLike)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.titleTextView.text = article.title
        holder.descriptionTextView.text = article.description ?: ""
        holder.likeTextView.text = if (article.isLiked) "♥" else "♡"

        // Update like button appearance
        updateLikeButton(holder.likeButton, article.isLiked)

        // Set click listener for like button
        holder.likeButton.setOnClickListener {
            onLikeClick(article)
            updateLikeButton(holder.likeButton, article.isLiked)
        }
        holder.descriptionTextView.text = article.description ?: ""
        holder.likeTextView.text = if (article.isLiked) "♥" else "♡"

        Glide.with(holder.imageView.context)
            .load(article.urlToImage)
            .placeholder(R.drawable.placeholder_image)
            .error(R.drawable.placeholder_image)
            .into(holder.imageView)
        holder.itemView.setOnClickListener {
            onArticleClick(article)
        }
    }

    override fun getItemCount(): Int = articles.size

    fun updateArticles(newArticles: List<Article>) {
        articles = newArticles
        notifyDataSetChanged()
    }

    private fun updateLikeButton(button: Button, isLiked: Boolean) {
        if (isLiked) {
            button.text = "❤️ Liked"
            button.setBackgroundColor(button.context.getColor(android.R.color.holo_red_light))
        } else {
            button.text = "🤍 Like"
            button.setBackgroundColor(button.context.getColor(android.R.color.darker_gray))
        }
    }
}