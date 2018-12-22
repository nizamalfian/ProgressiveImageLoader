package id.co.interactive.progressiveimageloader.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import id.co.interactive.progressiveimageloader.R

class AnotherActivity:AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_another)
    }

    override fun onBackPressed() {
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }

}
