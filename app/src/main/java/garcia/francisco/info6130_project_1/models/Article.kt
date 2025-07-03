package garcia.francisco.info6130_project_1.models

import java.io.Serializable

data class Article(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val content: String?,
    var isLiked: Boolean = false
) : Serializable {
    // Generate a unique ID based on title and URL
    val id: String
        get() = "${title.hashCode()}_${url?.hashCode() ?: 0}"
}