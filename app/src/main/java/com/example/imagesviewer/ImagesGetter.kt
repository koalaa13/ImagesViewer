package com.example.imagesviewer

import android.os.AsyncTask
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class ImagesGetter(
    private val imagesList: MutableList<Image>,
    private val adapter: RecyclerView.Adapter<RecyclerView.ViewHolder>?
) : AsyncTask<Unit, Unit, Unit>() {
    companion object {
        private const val TAG = "ImagesGetter"
        private const val ACCESS_KEY = "KwInqRg7a2rW1mivmJ4n2itI2_0roNjh5wcCkwDCAx8"
        private const val THEME = "cat"
        private const val ORIENTATION = "portrait"
        private const val FULL_URL: String =
            "https://api.unsplash.com/search/photos?per_page=50&query=$THEME&client_id=$ACCESS_KEY&orientation=$ORIENTATION"
    }

    override fun doInBackground(vararg params: Unit?) {
        Log.i(TAG, "doInBackground")

        val url = URL(FULL_URL)
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("Content-Type", "application/json")
        connection.requestMethod = "GET"
        if (connection.responseCode > 299) {
            Log.d(TAG, "Response code is " + connection.responseCode.toString())
        } else {
            val buffReader = BufferedReader(InputStreamReader(connection.inputStream))
            var inputLine: String?
            val json = StringBuffer()
            while (true) {
                inputLine = buffReader.readLine()
                if (inputLine == null) {
                    break
                }
                json.append(inputLine)
            }
            connection.disconnect()
            val jsonImages =
                JsonParser.parseString(json.toString()).asJsonObject.get("results").asJsonArray
            for (image in jsonImages) {
                val titleValue = image.asJsonObject.get("description")
                val toAdd = Image(
                    title = if (titleValue.isJsonNull) {
                        null
                    } else {
                        titleValue.asString
                    },
                    fullLink = image.asJsonObject.get("urls").asJsonObject.get("full").asString,
                    thumbLink = image.asJsonObject.get("urls").asJsonObject.get("thumb").asString
                )
                imagesList.add(toAdd)
            }
            Log.i(TAG, "Got ${imagesList.size} images")
        }
    }

    override fun onPostExecute(result: Unit?) {
        adapter?.notifyDataSetChanged()
    }
}