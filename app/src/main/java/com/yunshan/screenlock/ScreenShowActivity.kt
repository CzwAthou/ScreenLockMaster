package com.yunshan.screenlock

import android.os.Bundle
import android.os.Handler
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class ScreenShowActivity : AppCompatActivity() {

    companion object {
        var isShow = false
    }

    private val mHandler = Handler()
    private val switchImgRunnable = Runnable { switchNextImage() }

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_screen_show)

        viewPager = findViewById<ViewPager2>(R.id.viewPager)
        viewPager.adapter = ImagePagerAdapter(initImgList())

        val unlockBtn = findViewById<TextView>(R.id.unlockBtn)
        unlockBtn.setOnClickListener {
            finish()
        }

        isShow = true
        startSwitch()

        println("ScreenGuard ==>>  启动屏保页")
    }

    private fun initImgList(): List<Int> {
        val list = arrayListOf<Int>()
        list.add(R.drawable.screen_lock_bg01)
        list.add(R.drawable.screen_lock_bg02)
        list.add(R.drawable.screen_lock_bg03)
        list.add(R.drawable.screen_lock_bg04)
        list.add(R.drawable.screen_lock_bg05)
        return list
    }

    override fun onDestroy() {
        super.onDestroy()
        isShow = false
        mHandler.removeCallbacksAndMessages(null)
    }

    private fun startSwitch() {
        mHandler.removeCallbacks(switchImgRunnable)
        mHandler.postDelayed(switchImgRunnable, 3000)
    }

    private fun switchNextImage() {
        val currentIndex = viewPager.currentItem
        val total = viewPager.adapter?.itemCount ?: 0
        val nextIndex = (currentIndex + 1) % total
        viewPager.currentItem = nextIndex

        startSwitch()
    }

    inner class ImagePagerAdapter(private var imgList: List<Int>) :
        RecyclerView.Adapter<ImagePagerAdapter.ImgViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgViewHolder {
            val imageView = ImageView(parent.context)
            imageView.layoutParams = RecyclerView.LayoutParams(
                RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.MATCH_PARENT
            )
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            return ImgViewHolder(imageView)
        }

        override fun onBindViewHolder(holder: ImgViewHolder, position: Int) {
            holder.imgView.setImageResource(imgList[position])
        }

        override fun getItemId(position: Int): Long {
            return ViewPager2.NO_ID.toLong()
        }

        override fun getItemCount(): Int {
            return imgList.size
        }

        inner class ImgViewHolder(var imgView: ImageView) : RecyclerView.ViewHolder(imgView)
    }
}