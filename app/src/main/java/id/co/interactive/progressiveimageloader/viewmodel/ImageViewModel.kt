package id.co.interactive.progressiveimageloader.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.fetcher.ImageFetcher
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

/**
 * Created by nizamalfian on 22/12/2018.
 */

class ImageViewModel{
    private val disposable = CompositeDisposable()
    val bitmapResult=MutableLiveData<BitmapResult>()

    fun loadImages(startTime:Long,url:String,qualities:List<Int>){
        bitmapResult.value= BitmapResult.loading()
        val fetcher = ImageFetcher(Picasso.get(),startTime)
        disposable.add(fetcher.loadProgressively(url,qualities)
                .filter{getCurrentQuality()<it.quality}
                .subscribeBy(
                        onNext = {applyImage(it)},
                        onComplete = {postErrorIfNotSufficientQuality()}
                )
        )
    }

    private fun applyImage(bitmap: BitmapWithQuality?) {
        bitmapResult.value= BitmapResult.success(bitmap as BitmapWithQuality)
    }

    private fun postErrorIfNotSufficientQuality() {
        if(getCurrentQuality()<0)
            bitmapResult.value= BitmapResult.error()
    }

    private fun getCurrentQuality(): Int = bitmapResult.value?.quality?:-1

    fun unSubscribe() {
        disposable.dispose()
    }
}