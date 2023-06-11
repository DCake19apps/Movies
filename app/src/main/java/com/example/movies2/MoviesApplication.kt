package com.example.movies2

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.movies2.util.UnsplashSizingInterceptor

@HiltAndroidApp
class MoviesApplication: Application(){//}, ImageLoaderFactory {

//    override fun newImageLoader(): ImageLoader {
//        return ImageLoader.Builder(this)
//            .components {
//                add(UnsplashSizingInterceptor)
//            }
//            .build()
//    }
}