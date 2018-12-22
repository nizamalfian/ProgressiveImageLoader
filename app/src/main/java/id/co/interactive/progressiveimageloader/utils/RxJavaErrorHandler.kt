package id.co.interactive.progressiveimageloader.utils

import io.reactivex.exceptions.UndeliverableException
import io.reactivex.functions.Consumer

class RxJavaErrorHandler:Consumer<Throwable>{
    override fun accept(t: Throwable) {
        if(t is UndeliverableException){
            //Ignore or log exception
        }else{
            throw t
        }
    }
}