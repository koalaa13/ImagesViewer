package com.example.imagesviewer

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

class ImageViewHolder(val root: View) : RecyclerView.ViewHolder(root) {
    fun bind(image: Image) {
        with(root) {
            DownloadImage(thumb_image).execute(image.thumbLink)
            if (image.title != null) {
                image_title.text = image.title
            }
        }
    }
}