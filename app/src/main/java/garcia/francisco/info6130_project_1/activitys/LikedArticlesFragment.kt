package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.adapters.ArticleAdapter
import garcia.francisco.info6130_project_1.databinding.ActivityLikedArticlesBinding
import garcia.francisco.info6130_project_1.models.Article
import garcia.francisco.info6130_project_1.utils.LikePreferencesHelper
import garcia.francisco.info6130_project_1.viewModels.MainViewModel
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.repository.ArticleRepository

class LikedArticlesFragment : Fragment() {
    private lateinit var binding: ActivityLikedArticlesBinding
    private lateinit var adapter: ArticleAdapter
    private lateinit var likePreferencesHelper: LikePreferencesHelper
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityLikedArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        likePreferencesHelper = LikePreferencesHelper(requireContext())
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecyclerView()
        loadLikedArticles()
    }

    override fun onResume() {
        super.onResume()
        loadLikedArticles()
    }

    private fun setupRecyclerView() {
        adapter = ArticleAdapter(emptyList()) { article ->
            // Optional: Handle item clicks
        }
        binding.recyclerViewLikedArticles.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewLikedArticles.adapter = adapter
    }

    private fun loadLikedArticles() {
        val likedIds = likePreferencesHelper.getAllLikedArticles()
        val allArticles = ArticleRepository.articles
        val likedArticles = allArticles.filter { likedIds.contains(it.id) }

        adapter.updateArticles(likedArticles)
        binding.tvEmptyState.visibility = if (likedArticles.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
} 