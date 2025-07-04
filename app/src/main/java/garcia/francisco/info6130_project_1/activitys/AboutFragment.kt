package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        setupContent()
    }

    private fun setupContent() {
        // App description
        binding.tvAppDescription.text = getString(R.string.app_description)
        
        // App features
        binding.tvAppFeatures.text = getString(R.string.app_features)
        
        // Group members
        binding.tvGroupMembers.text = getString(R.string.group_members)
        
        // Course info
        binding.tvCourseInfo.text = getString(R.string.course_info)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 