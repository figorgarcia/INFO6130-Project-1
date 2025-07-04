package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.databinding.ActivityMainTabBinding

class MainTabActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainTabBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTabBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Load the first tab by default
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, ArticlesFragment())
                .commit()
        }

        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, ArticlesFragment())
                        .commit()
                    true
                }

                R.id.nav_topHeadlines -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, TopHeadlinesFragment())
                        .commit()
                    true
                }

                R.id.nav_sources -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, SourcesFragment())
                        .commit()
                    true
                }

                R.id.nav_liked -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, LikedArticlesFragment())
                        .commit()
                    true
                }

                R.id.nav_about -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.nav_host_fragment, AboutFragment())
                        .commit()
                    true
                }
                else -> false
            }
        }
    }
}