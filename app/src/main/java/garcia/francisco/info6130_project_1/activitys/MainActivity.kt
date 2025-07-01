package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import garcia.francisco.info6130_project_1.databinding.ActivityMainBinding
import garcia.francisco.info6130_project_1.repository.NewsRepository
import garcia.francisco.info6130_project_1.viewModels.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit private var binding: ActivityMainBinding;
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        viewModel._articles.observe(this) { articles ->
            if (articles.isNotEmpty()) {
                binding.tvTitleResult.text = articles.first().title
            } else {
                binding.tvTitleResult.text = "No results found."
            }
        }

    }

    fun onLoadClick(view: View) {
        viewModel.loadNews(1);

    }
}