package com.pedulinegeri.unjukrasa.demonstration

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.pedulinegeri.unjukrasa.MainFragmentDirections
import com.pedulinegeri.unjukrasa.databinding.DemonstrationImageItemBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.squareup.picasso.Picasso


class DemonstrationImageAdapter(
    private val navController: NavController
) :
    RecyclerView.Adapter<DemonstrationImageAdapter.ViewHolder>() {

    private var videoImagesUrl = arrayListOf<String>()

    inner class ViewHolder(private val binding: DemonstrationImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(uri: String) {
            if (uri.length == 11) {
                binding.youtubePlayerView.visibility = View.VISIBLE
                binding.ivDemonstration.visibility = View.GONE

                binding.youtubePlayerView.addYouTubePlayerListener(object :
                    AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        super.onReady(youTubePlayer)

                        youTubePlayer.cueVideo(uri, 0f)
                    }
                })
            } else if (uri.isNotBlank()) {
                binding.youtubePlayerView.visibility = View.GONE
                binding.ivDemonstration.visibility = View.VISIBLE

                Picasso.get().load(uri).into(binding.ivDemonstration)
                binding.ivDemonstration.setOnClickListener {
                    navController.navigate(
                        MainFragmentDirections.actionGlobalImageZoomBottomSheetDialog(
                            uri
                        )
                    )
                }
            }
        }
    }

    fun addImageOrVideo(url: String) {
        this.videoImagesUrl.add(url)
        notifyItemChanged(itemCount - 1)
    }

    fun initDemonstrationImageList(imagesUrl: ArrayList<String>) {
        this.videoImagesUrl = imagesUrl
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = DemonstrationImageItemBinding.inflate(
            LayoutInflater.from(viewGroup.context),
            viewGroup,
            false
        )

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(videoImagesUrl[position])
    }

    override fun getItemCount(): Int = videoImagesUrl.size
}