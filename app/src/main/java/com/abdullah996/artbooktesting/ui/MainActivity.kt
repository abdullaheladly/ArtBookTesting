package com.abdullah996.artbooktesting.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdullah996.artbooktesting.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //setup fragment factory
    @Inject lateinit var fragmentFactory:ArtFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setup fragment factory
        supportFragmentManager.fragmentFactory=fragmentFactory
        setContentView(R.layout.activity_main)
    }
}