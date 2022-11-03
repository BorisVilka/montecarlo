package com.monte.carlo

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.monte.carlo.databinding.ActivityGameBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ActivityGame : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        try {
            val ips = assets.open("back1.png")
            val drawable = Drawable.createFromStream(ips,null)
            binding.imageView6.setImageDrawable(drawable)
            ips.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        setContentView(binding.root)
        binding.list1.adapter = SpinAdapter()
        binding.list2.adapter = SpinAdapter()
        binding.list3.adapter = SpinAdapter()
        binding.list4.adapter = SpinAdapter()
        binding.list5.adapter = SpinAdapter()

        binding.list1.setOnTouchListener { p0, p1 -> true }
        binding.list2.setOnTouchListener { p0, p1 -> true }
        binding.list3.setOnTouchListener { p0, p1 -> true }
        binding.list4.setOnTouchListener { p0, p1 -> true }
        binding.list5.setOnTouchListener { p0, p1 -> true }


        binding.button3.setOnClickListener {
            binding.button3.isEnabled = false
            val player = MediaPlayer.create(this@ActivityGame,R.raw.music)
            player.start()
            binding.list1.smoothScrollToPosition((binding.list1.adapter as RecyclerView.Adapter).itemCount-2)
            binding.list2.smoothScrollToPosition((binding.list2.adapter as RecyclerView.Adapter).itemCount-2)
            binding.list3.smoothScrollToPosition((binding.list3.adapter as RecyclerView.Adapter).itemCount-2)
            binding.list4.smoothScrollToPosition((binding.list4.adapter as RecyclerView.Adapter).itemCount-2)
            binding.list5.smoothScrollToPosition((binding.list5.adapter as RecyclerView.Adapter).itemCount-2)
            var completable = Completable.create {
                runOnUiThread {
                    binding.imageView7.visibility = View.VISIBLE
                    binding.button3.isEnabled = true
                    binding.list1.scrollToPosition(0)
                    binding.list2.scrollToPosition(0)
                    binding.list3.scrollToPosition(0)
                    binding.list4.scrollToPosition(0)
                    binding.list5.scrollToPosition(0)
                }
                it.onComplete()
            }.delaySubscription(10,TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            completable.subscribe()
        }
    }
}