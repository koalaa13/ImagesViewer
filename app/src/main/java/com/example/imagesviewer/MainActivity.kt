package com.example.imagesviewer

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        container.apply {
            layoutManager = viewManager
            adapter = ImageAdapter(imagesList) {
                Toast.makeText(
                    this@MainActivity,
                    "Clicked on image# $it",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        ImagesGetter(imagesList, container.adapter).execute()
    }
}