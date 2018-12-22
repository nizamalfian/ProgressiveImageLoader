package id.co.interactive.progressiveimageloader.main

import android.graphics.Bitmap
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult

/**
 * Created by nizamalfian on 22/12/2018.
 */

interface MainView {
    fun process(result:BitmapResult?)
    fun showImage(bitmap:Bitmap,startTime:Long)
    fun showProgress()
    fun hideProgress()
    fun showError()
    fun showTimeCounterResult(startTime:Long,isUsingRxJava:Boolean)
}