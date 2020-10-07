package com.example.imagesviewer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    var imagesList = emptyList<Image>().toMutableList()
    private val fullhdImagesDownloads: Queue<DownloadImage> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
        val viewManager = LinearLayoutManager(this)

        val onCLick = View.OnClickListener() {
            while (!fullhdImagesDownloads.isEmpty()) {
                val cur = fullhdImagesDownloads.poll()
                cur?.cancel(true)
            }
            it.visibility = View.GONE
        }
        fullhd_image.setOnClickListener(onCLick)
        fullhd_image_downloading_progressbar.setOnClickListener(onCLick)


        myRecyclerView.apply {
            layoutManager = viewManager
            adapter = ImageAdapter(imagesList) {
                fullhdImagesDownloads.add(
                    DownloadImage(
                        fullhd_image,
                        listOf<View>(fullhd_image_downloading_progressbar)
                    )
                )
                fullhdImagesDownloads.peek()?.execute(it.fullLink)
                fullhd_image_downloading_progressbar.visibility = View.VISIBLE
            }
        }

        ImagesGetter(imagesList, myRecyclerView.adapter).execute()
    }
}