package id.co.interactive.progressiveimageloader.main

import android.arch.lifecycle.Observer
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import id.co.interactive.progressiveimageloader.R
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult
import id.co.interactive.progressiveimageloader.fetcher.data.ResponseState.*
import id.co.interactive.progressiveimageloader.viewmodel.ImageViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),MainView{
    private val viewModel=ImageViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.bitmapResult.observe(this, Observer<BitmapResult>{ it -> process(it) })
        viewModel.loadImages(listOf(3000,10,300))
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
        imageView.setImageBitmap(bitmap)
    }

    override fun showProgress() {
        loader.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        loader.visibility = View.GONE
    }

    override fun showError() {
        errorText.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unSubscribe()
    }
}
