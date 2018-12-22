package id.co.interactive.progressiveimageloader.utils

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer

/**
 * Created by nizamalfian on 22/12/2018.
 */

class RxJavaErrorHandler:Consumer<Throwable>{
    override fun accept(t: Throwable) {
        if(t is UndeliverableException){
            //Ignore or log exception
        }else{
            throw t
        }
    }
}