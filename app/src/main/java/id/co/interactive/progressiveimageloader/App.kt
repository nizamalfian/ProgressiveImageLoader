package id.co.interactive.progressiveimageloader

import android.app.Application
import com.squareup.picasso.Picasso
import id.co.interactive.progressiveimageloader.utils.RxJavaErrorHandler
import io.reactivex.plugins.RxJavaPlugins

class App:Application(){
    override fun onCreate() {
        super.onCreate()
        RxJavaPlugins.setErrorHandler(RxJavaErrorHandler())
        Picasso.get().isLoggingEnabled=true
    }
}