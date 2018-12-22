package id.co.interactive.progressiveimageloader.fetcher

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.SingleEmitter
import java.lang.Exception

class CustomImageLoadTarget(private val emitter:SingleEmitter<BitmapWithQuality>,
                            private val quality:Int,
                            private val unSubscribe:(Target)->Unit):Target {

    init {
        emitter.setCancellable{unSubscribe(this)}
    }

    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        //do nothing
    }

    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
        emitter.tryOnError(e as Exception)
        unSubscribe(this)
    }

    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
        emitter.onSuccess(BitmapWithQuality(bitmap as Bitmap, quality))
        unSubscribe(this)
    }

}