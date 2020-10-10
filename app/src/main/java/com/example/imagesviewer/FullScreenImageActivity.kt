package com.example.imagesviewer

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.imagesviewer.MainActivity.Companion.imagesMap
import kotlinx.android.synthetic.main.fullhd_image.*
import java.util.*

class FullScreenImageActivity : AppCompatActivity() {
    companion object {
        const val TAG = "FullScreenImageActivity"
    }

    private val fullhdImagesDownloads: Queue<DownloadImage> = LinkedList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fullhd_image)
        Log.i(TAG, "onCreate")

        val onCLick = View.OnClickListener() {
            while (!fullhdImagesDownloads.isEmpty()) {
                val cur = fullhdImagesDownloads.poll()
                cur?.cancel(true)
            }
            finish()
        }
        fullhd_image.setOnClickListener(onCLick)
        fullhd_image_downloading_progressbar.setOnClickListener(onCLick)

        fullhdImagesDownloads.add(
            DownloadImage(
                imagesMap,
                fullhd_image,
                listOf(fullhd_image_downloading_progressbar)
            )
        )
        fullhdImagesDownloads.peek()?.execute(intent.getStringExtra("linkToDownload"))
    }
}