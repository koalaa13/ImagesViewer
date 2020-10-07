package com.example.imagesviewer

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    var imagesList = emptyList<Image>().toMutableList()
    private val imagesMap = ConcurrentHashMap<String, Bitmap>()
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
            adapter = ImageAdapter(imagesList, imagesMap) {
                fullhdImagesDownloads.add(
                    DownloadImage(
                        imagesMap,
                        fullhd_image,
                        listOf(fullhd_image_downloading_progressbar)
                    )
                )
                fullhdImagesDownloads.peek()?.execute(it.fullLink)
                fullhd_image_downloading_progressbar.visibility = View.VISIBLE
            }
        }

        ImagesGetter(imagesList, myRecyclerView.adapter).execute()
    }
}