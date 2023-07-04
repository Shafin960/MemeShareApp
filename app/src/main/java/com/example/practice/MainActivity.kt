package com.example.practice

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    var currentIamgeUrl : String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme(){
        // Instantiate the RequestQueue.
        val progress=findViewById<ProgressBar>(R.id.progressBar)
        progress.visibility=View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        currentIamgeUrl = "https://meme-api.com/gimme/wholesomememes"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentIamgeUrl,null,
            { response ->
                currentIamgeUrl= response.getString("url")
                val memeImage=findViewById<ImageView>(R.id.memeImage)
                Glide.with(this).load(currentIamgeUrl).listener(object:RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false

                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }
                }).into(memeImage)
            },
            {

            })
        queue.add(jsonObjectRequest)
    }


    fun  shareMeme(view: View){
        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Look at this cool Meme $currentIamgeUrl")
        val chooser= Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)
    }

    fun nextMeme(view: View){
        loadMeme()
    }
}