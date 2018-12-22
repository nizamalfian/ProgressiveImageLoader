package id.co.interactive.progressiveimageloader.fetcher

import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.fetcher.data.BitmapWithQuality
import io.reactivex.Observable
import io.reactivex.Single

class ImageFetcher(private val picasso:Picasso){
    fun loadProgressively(baseUrl: String,qualities:List<Int>):Observable<BitmapWithQuality>{
        return qualities
                .map { quality -> Pair(createUrl(baseUrl,quality),quality) }
                .map {loadImageAndIgnoreError(it)}
                .reduce { o1, o2 -> Observable.merge(o1,o2)}
    }

    fun loadProgressively(baseUrl:String,quality1:Int,quality2:Int):Observable<BitmapWithQuality>{
        return Observable.merge(
                loadImageAndIgnoreError(createUrl(baseUrl,quality1),quality2),
                loadImageAndIgnoreError(createUrl(baseUrl,quality1),quality2)
        )
    }

    fun loadImageAndIgnoreError(urlWithQuality:Pair<String,Int>):Observable<BitmapWithQuality>{
        val (url,quality)=urlWithQuality
        return loadImageAndIgnoreError(url,quality)
    }

    fun loadImageAndIgnoreError(url:String,quality:Int):Observable<BitmapWithQuality>{
        return Single
                .create(ImageFetcherSingleSubscribe(picasso, url, quality))
                .toObservable()
                .onErrorResumeNext(Observable.empty<BitmapWithQuality>())
    }

    private fun createUrl(url:String,size:Int):String="$url/$size/$size?image=0" //?image=0 added so image wont be random
}