package com.example.imagesviewer

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
    }

    var imagesList = emptyList<Image>().toMutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
        val viewManager = LinearLayoutManager(this)

        fullhd_image.setOnClickListener {
            it.visibility = View.GONE
            if (it is ImageView) {
                it.setImageResource(R.drawable.no_image)
            }
        }

        myRecyclerView.apply {
            layoutManager = viewManager
            adapter = ImageAdapter(imagesList) {
                DownloadImage(fullhd_image).execute(it.fullLink)
                fullhd_image.visibility = View.VISIBLE
            }
        }

        ImagesGetter(imagesList, myRecyclerView.adapter).execute()
    }
}