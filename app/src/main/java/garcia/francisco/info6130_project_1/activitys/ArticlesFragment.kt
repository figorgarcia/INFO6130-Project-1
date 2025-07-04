package garcia.francisco.info6130_project_1.activitys

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.ViewGroup
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityMainBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.repository.ArticleRepository
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel

class ArticlesFragment : Fragment() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var articleAdapter: ArticleAdapter
    private lateinit var likePreferencesHelper: LikePreferencesHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        likePreferencesHelper = LikePreferencesHelper(requireContext())

        setupRecyclerView()
        observeViewModel()

        // Load button to fetch articles
        binding.btnLoad.setOnClickListener {
            viewModel.loadNews(1)
        }

        // Hide the liked articles button (removed from UI)
        binding.btnLikedArticles.visibility = View.GONE
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter(emptyList()) { article ->
            val intent = ArticleDetailActivity.newIntent(requireContext(), article)
            startActivity(intent)
        }

        binding.recyclerViewArticles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
    }

    private fun observeViewModel() {
        viewModel._articles.observe(viewLifecycleOwner) { articles ->
            if (articles.isNotEmpty()) {
                articles.forEach { article ->
//                    article.isLiked = likePreferencesHelper.getLikeState(it.id)
                }
                ArticleRepository.articles = articles
                articleAdapter.updateArticles(articles)
                binding.tvTitleResult.text = "${articles.size} articles loaded"
                binding.recyclerViewArticles.visibility = View.VISIBLE
            } else {
                binding.tvTitleResult.text = "No results found."
                binding.recyclerViewArticles.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val articles = viewModel._articles.value ?: return
        articles.forEach { it.isLiked = likePreferencesHelper.getLikeState(it.id) }
        articleAdapter.updateArticles(articles)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}