package com.example.imagesviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.io.IOException
import java.net.URL
import java.util.concurrent.ConcurrentHashMap


class DownloadImage(
    private val imagesMap: ConcurrentHashMap<String, Bitmap>,
    private val image: ImageView,
    private val toMakeInvisible: List<View>
) :
    AsyncTask<String, Unit, String?>() {
    companion object {
        const val TAG = "DownloadImage"
    }

    override fun doInBackground(vararg params: String?): String? {
        Log.i(TAG, "doInBackground, size of imagesMap is ${imagesMap.size}")
        return try {
            val urlString = params[0]!!
            if (!imagesMap.contains(urlString)) {
                val url = URL(urlString)
                imagesMap[urlString] = BitmapFactory.decodeStream(url.openStream())
            }
            urlString
        } catch (e: IOException) {
            Log.e(TAG, e.message!!)
            null
        }
    }

    override fun onPostExecute(result: String?) {
        if (result != null && !isCancelled) {
            Log.i(TAG, "Set bitmap for image")
            image.setImageBitmap(imagesMap[result])
            for (v in toMakeInvisible) {
                v.visibility = View.GONE
            }
            image.visibility = View.VISIBLE
        }
    }
}