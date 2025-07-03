package garcia.francisco.info6130_project_1.activitys

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import garcia.francisco.info6130_project_1.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About"

        binding.tvAppDescription.text = "A modern news app for browsing, reading, and liking the latest Kotlin news articles."
        binding.tvGroupMembers.text = """
            Group Members:
            • Aragon, Edwin Santiago
            • Garcia, Francisco Igor
            • Nguyen, Cao Hai Dang
            • Shah, Jenish Sunil
        """.trimIndent()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
} 