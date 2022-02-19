package io.radev.roman

import android.app.Application
import io.radev.roman.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

/*
 * Created by Radoslaw on 18/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */


class RomanApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@RomanApplication)
            modules(appModule)
        }
    }
}