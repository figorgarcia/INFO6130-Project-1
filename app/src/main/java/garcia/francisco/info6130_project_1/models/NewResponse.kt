package garcia.francisco.info6130_project_1.models

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)