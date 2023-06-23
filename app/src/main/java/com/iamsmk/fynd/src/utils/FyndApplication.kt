package com.iamsmk.fynd.src.utils

import android.app.Application
import com.google.android.material.color.DynamicColors

class MyApplication: Application() {
//    private val _instance: FyndApplication? = null
    override fun onCreate() {
        super.onCreate()
        DynamicColors.applyToActivitiesIfAvailable(this)
    }

//    @Synchronized
//    fun getInstance(): FyndApplication? {
//        return FyndApplication._instance
//    }
}