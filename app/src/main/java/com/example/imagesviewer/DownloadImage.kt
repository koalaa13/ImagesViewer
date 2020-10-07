package com.example.imagesviewer

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.View
import android.widget.ImageView
import java.io.IOException
import java.net.URL


class DownloadImage(
    private val image: ImageView,
    private var toMakeInvisible: List<View> = emptyList()
) :
    AsyncTask<String, Unit, Bitmap?>() {
    companion object {
        const val TAG = "DownloadImage"
    }

    override fun doInBackground(vararg params: String?): Bitmap? {
        Log.i(TAG, "doInBackground")
        return try {
            val url = URL(params[0]!!)
            BitmapFactory.decodeStream(url.openStream())
        } catch (e: IOException) {
            Log.e(TAG, e.message!!)
            null
        }
    }

    override fun onPostExecute(result: Bitmap?) {
        if (result != null && !isCancelled) {
            Log.i(TAG, "Set bitmap for image")
            image.setImageBitmap(result)
            for (v in toMakeInvisible) {
                v.visibility = View.GONE
            }
            image.visibility = View.VISIBLE
        }
    }
}