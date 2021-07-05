package com.pedulinegeri.unjukrasa.demonstration.progress

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pedulinegeri.unjukrasa.R
import com.pedulinegeri.unjukrasa.databinding.ProgressListItemBinding
import com.pedulinegeri.unjukrasa.demonstration.DemonstrationImageAdapter


class ProgressListAdapter(private val dataSet: List<String>) :
    RecyclerView.Adapter<ProgressListAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(private val binding: ProgressListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(text: String) {
            binding.reContent.setEditorFontSize(16)
            binding.reContent.setEditorFontColor(binding.textView7.currentTextColor)
            binding.reContent.setEditorBackgroundColor(binding.root.solidColor)
            binding.reContent.setInputEnabled(false)
            binding.reContent.html = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris"

            binding.vpImages.adapter = DemonstrationImageAdapter(listOf(R.drawable.indonesian_flag))
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val binding = ProgressListItemBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.bind(dataSet[position])
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

}