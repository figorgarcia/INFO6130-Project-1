package garcia.francisco.info6130_project_1.utils

import android.content.Context
import android.content.SharedPreferences

class LikePreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("article_likes", Context.MODE_PRIVATE)

    fun saveLikeState(articleId: String, isLiked: Boolean) {
        sharedPreferences.edit().putBoolean(articleId, isLiked).apply()
    }

    fun getLikeState(articleId: String): Boolean {
        return sharedPreferences.getBoolean(articleId, false)
    }

    fun getAllLikedArticles(): Set<String> {
        return sharedPreferences.all.filterValues { it == true }.keys
    }
}