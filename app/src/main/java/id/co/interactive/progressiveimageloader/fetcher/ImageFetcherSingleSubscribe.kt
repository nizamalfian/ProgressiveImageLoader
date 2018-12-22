package id.co.interactive.progressiveimageloader.fetcher

import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.SingleEmitter
import io.reactivex.SingleOnSubscribe

class ImageFetcherSingleSubscribe(private val picasso: Picasso,
                                  private val url:String,
                                  private val quality:Int):SingleOnSubscribe<BitmapWithQuality>{
    private val runningTargets= mutableListOf<Target>()

    override fun subscribe(emitter: SingleEmitter<BitmapWithQuality>) {
        val target= CustomImageLoadTarget(emitter, quality) {
            removeTargetAndCancelRequest(it)
        }

        runningTargets.add(target)
        picasso.load(url)
                .into(target)
    }

    private fun removeTargetAndCancelRequest(target: Target) {
        picasso.cancelRequest(target)
        runningTargets.remove(target)
    }

}