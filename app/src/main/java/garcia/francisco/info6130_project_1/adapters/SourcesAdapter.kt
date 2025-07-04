package garcia.francisco.info6130_project_1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import garcia.francisco.info6130_project_1.R
import garcia.francisco.info6130_project_1.models.Source

class SourcesAdapter(private var sources: List<Source>) : RecyclerView.Adapter<SourcesAdapter.SourceViewHolder>() {
    
    class SourceViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.tvSourceName)
        val description: TextView = itemView.findViewById(R.id.tvSourceDescription)
        val category: TextView = itemView.findViewById(R.id.tvSourceCategory)
        val country: TextView = itemView.findViewById(R.id.tvSourceCountry)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return SourceViewHolder(view)
    }

    override fun onBindViewHolder(holder: SourceViewHolder, position: Int) {
        val source = sources[position]
        holder.name.text = source.name
        holder.description.text = source.description
        holder.category.text = "Category: ${source.category.capitalize()}"
        holder.country.text = "Country: ${source.country.uppercase()}"
    }

    override fun getItemCount(): Int = sources.size

    fun updateSources(newSources: List<Source>) {
        sources = newSources
        notifyDataSetChanged()
    }
} 