package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.adapters.SourcesAdapter
import garcia.francisco.info6130_project_1.databinding.FragmentSourcesBinding
import garcia.francisco.info6130_project_1.models.Source
import garcia.francisco.info6130_project_1.models.SourcesResponse
import garcia.francisco.info6130_project_1.provider.NewsRetrofitProvider
import garcia.francisco.info6130_project_1.interfaces.NewsInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SourcesFragment : Fragment() {
    private var _binding: FragmentSourcesBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SourcesAdapter
    private val categories = listOf("business", "entertainment", "general", "health", "science", "sports", "technology")
    private val apiKey = "002a220aea45414a8000d5fb4879b943" // Your API key

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSourcesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupRecyclerView()
        
        // Load sources for the first category by default
        fetchSources(categories[0])
    }

    private fun setupSpinner() {
        val spinnerAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategory.adapter = spinnerAdapter

        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                fetchSources(categories[position])
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun setupRecyclerView() {
        adapter = SourcesAdapter(emptyList())
        binding.recyclerViewSources.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewSources.adapter = adapter
    }

    private fun fetchSources(category: String) {
        val api = NewsRetrofitProvider.retrofitInstance.create(NewsInterface::class.java)
        
        api.getSources(category, apiKey).enqueue(object : Callback<SourcesResponse> {
            override fun onResponse(call: Call<SourcesResponse>, response: Response<SourcesResponse>) {
                if (response.isSuccessful) {
                    val sources = response.body()?.sources ?: emptyList()
                    adapter.updateSources(sources)
                    binding.tvEmptyState.visibility = if (sources.isEmpty()) View.VISIBLE else View.GONE
                } else {
                    binding.tvEmptyState.visibility = View.VISIBLE
                    binding.tvEmptyState.text = "Error loading sources"
                }
            }

            override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                binding.tvEmptyState.visibility = View.VISIBLE
                binding.tvEmptyState.text = "Network error: ${t.message}"
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 