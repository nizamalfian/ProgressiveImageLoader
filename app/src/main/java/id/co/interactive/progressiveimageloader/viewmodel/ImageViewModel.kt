package id.co.interactive.progressiveimageloader.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.fetcher.ImageFetcher
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapResult
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy

class ImageViewModel {
    companion object {
        private const val BASE_IMAGE_URL="https://cloud.interactive.co.id//myprofit//images//company_logo//181024-082731-387.png"
    }

    private val disposable = CompositeDisposable()
    private val fetcher = ImageFetcher(Picasso.get())
    val bitmapResult=MutableLiveData<BitmapResult>()

    fun loadImages(qualities:List<Int>){
        bitmapResult.value= BitmapResult.loading()
        disposable.add(fetcher.loadProgressively(BASE_IMAGE_URL,qualities)
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