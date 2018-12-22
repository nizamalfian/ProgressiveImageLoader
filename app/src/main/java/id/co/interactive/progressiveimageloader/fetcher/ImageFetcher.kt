package id.co.interactive.progressiveimageloader.fetcher

import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Created by nizamalfian on 22/12/2018.
 */

class ImageFetcher(private val picasso: Picasso, private val startTime: Long){
    fun loadProgressively(imageUrl: String, qualities:List<Int>):Observable<BitmapWithQuality>{
        return qualities
                .map { quality -> Pair(createUrl(imageUrl),quality) }
                .map {loadImageAndIgnoreError(it)}
                .reduce { o1, o2 -> Observable.merge(o1,o2)}
    }

    fun loadImageAndIgnoreError(urlWithQuality:Pair<String,Int>):Observable<BitmapWithQuality>{
        val (url,quality)=urlWithQuality
        return loadImageAndIgnoreError(url,quality)
    }

    fun loadImageAndIgnoreError(url:String,quality:Int):Observable<BitmapWithQuality>{
        return Single
                .create(ImageFetcherSingleSubscribe(picasso, url, quality, startTime))
                .toObservable()
                .onErrorResumeNext(Observable.empty<BitmapWithQuality>())
    }

    private fun createUrl(url:String):String=url
}