package com.example.imagesviewer

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.ConcurrentHashMap

class MainActivity : AppCompatActivity() {
    companion object {
        const val TAG = "MainActivity"
        var imagesMap = ConcurrentHashMap<String, Bitmap>()
        private var imagesList = emptyList<Image>().toMutableList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i(TAG, "onCreate")
        val viewManager = LinearLayoutManager(this)
        val openFullScreen = Intent(this, FullScreenImageActivity::class.java)

        myRecyclerView.apply {
            layoutManager = viewManager
            adapter = ImageAdapter(imagesList, imagesMap) {
                startActivity(openFullScreen.apply {
                    putExtra("linkToDownload", it.fullLink)
                })
            }
        }

        if (imagesList.isEmpty()) {
            ImagesGetter(imagesList, myRecyclerView.adapter).execute()
        }
    }
}