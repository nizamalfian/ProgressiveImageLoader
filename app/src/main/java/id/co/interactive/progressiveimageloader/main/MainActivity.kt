package id.co.interactive.progressiveimageloader.main

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.View
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.BuildConfig
import id.co.interactive.progressiveimageloader.R
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult
import id.co.interactive.progressiveimageloader.fetcher.data.ResponseState.*
import id.co.interactive.progressiveimageloader.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity(),MainView{
    private val viewModel=ImageViewModel()
    private val imageUrl=BuildConfig.IMAGE_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadImageWithoutRxJava()
        loadImageWithRxJava()
    }

    override fun process(result: BitmapResult?) {
        result?.let{
            when(it.state){
                LOADING ->{
                    showProgress()
                }
                ERROR->{
                    hideProgress()
                    showError()
                }
                SUCCESS->{
                    hideProgress()
                    it.bitmap?.let { bitmap -> showImage(bitmap,result.startTime as Long) }
                }
            }
        }
    }

    override fun showImage(bitmap: Bitmap,startTime:Long) {
        img1.setImageBitmap(bitmap)
        showTimeCounterResult(startTime,true)
    }

    override fun showProgress() {
        img1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.img_placeholder))
    }

    override fun hideProgress() {
        img1.setImageDrawable(null)
    }

    override fun showError() {
        img1.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.img_error_image))
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribe()
    }

    @SuppressLint("SetTextI18n")
    override fun showTimeCounterResult(startTime:Long,isUsingRxJava:Boolean) {
        val difference:Double=(((System.nanoTime()-startTime)/ 1000000)*0.001)
        val df = DecimalFormat("#.####")
        val result="${df.format(difference)} seconds"
        if(isUsingRxJava)
            txtTimeCounted1.text=result
        else
            txtTimeCounted2.text=result
    }

    private fun loadImageWithRxJava(){
        viewModel.bitmapResult.observe(this, Observer<BitmapResult>{ it -> process(it) })
        viewModel.loadImages(System.nanoTime(),imageUrl,listOf(3000,10,300))//<-Just random qualities
    }

    private fun loadImageWithoutRxJava(){
        val startTime=System.nanoTime()
        Picasso.get().load(imageUrl).placeholder(R.drawable.img_placeholder).error(R.drawable.img_error_image)
                .into(img2, object : Callback {
                    override fun onSuccess() {
                        showTimeCounterResult(startTime,false)
                    }

                    override fun onError(e: Exception) {

                    }
                })
    }

    fun moveToAnotherPage(v:View){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
}
