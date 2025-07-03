package garcia.francisco.info6130_project_1.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import garcia.francisco.info6130_project_1.R

object GlideImageLoader {

    fun loadImage(context: Context, imageUrl: String?, imageView: ImageView) {
        val requestOptions = RequestOptions()
            .placeholder(R.drawable.ic_placeholder)
            .error(R.drawable.ic_error)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()

        Glide.with(context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(imageView)
    }
}
