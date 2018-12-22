package id.co.interactive.progressiveimageloader.fetcher.data

import android.graphics.Bitmap
import id.co.interactive.progressiveimageloader.fetcher.data.ResponseState.*

class BitmapResult private constructor(val state:ResponseState,
                                       val quality:Int=-1,
                                       val bitmap:Bitmap?=null,
                                       val startTime:Long?=0){

    companion object {
        fun loading()=BitmapResult(LOADING)
        fun error()=BitmapResult(ERROR)
        fun success(bitmapWithQuality: BitmapWithQuality)=BitmapResult(SUCCESS,bitmapWithQuality.quality,bitmapWithQuality.bitmap,bitmapWithQuality.startTime)
    }
}
