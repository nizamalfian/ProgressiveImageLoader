package id.co.interactive.progressiveimageloader.fetcher.data

import android.graphics.Bitmap
import id.co.interactive.progressiveimageloader.fetcher.data.ResponseState.*

/**
 * Created by nizamalfian on 22/12/2018.
 */

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
