package id.co.interactive.progressiveimageloader.main

import android.graphics.Bitmap
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult

interface MainView {
    fun process(result:BitmapResult?)
    fun showImage(bitmap:Bitmap,startTime:Long)
    fun showProgress()
    fun hideProgress()
    fun showError()
    fun showTimeCounterResult(startTime:Long,isUsingRxJava:Boolean)
}