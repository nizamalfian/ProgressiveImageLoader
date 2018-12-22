package id.co.interactive.progressiveimageloader.main

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
                    it.bitmap?.let { bitmap -> showImage(bitmap) }
                }
            }
        }
    }

    override fun showImage(bitmap: Bitmap) {
        img1.setImageBitmap(bitmap)
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

    private fun loadImageWithRxJava(){
        viewModel.bitmapResult.observe(this, Observer<BitmapResult>{ it -> process(it) })
        viewModel.loadImages(imageUrl,listOf(3000,10,300))//<-Just random qualities
    }

    private fun loadImageWithoutRxJava(){
        Picasso.get().load(imageUrl).placeholder(R.drawable.img_placeholder).error(R.drawable.img_error_image).into(img2, object : Callback {
            override fun onSuccess() {

            }

            override fun onError(e: Exception) {

            }
        })
    }

    fun moveToAnotherPage(v:View){
        startActivity(Intent(this,AnotherActivity::class.java))
        finish()
    }
}
