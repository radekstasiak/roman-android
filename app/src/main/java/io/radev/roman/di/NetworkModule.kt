package io.radev.roman.di

import io.ktor.client.engine.android.*
import io.radev.roman.R
import io.radev.roman.network.ApiClient
import io.radev.roman.network.PlacesService
import io.radev.roman.network.PlacesServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.bind
import org.koin.dsl.module

/*
 * Created by Radoslaw on 20/02/2022.
 * radev.io 2022.
 * Peace and Love.
 */

val networkModule = module {
    single {
        ApiClient(
            engine = Android.create(),
            apiKey = androidContext().resources.getString(R.string.FOURSQUARE_API_KEY)
        )
    }
    single { PlacesServiceImpl(get()) } bind PlacesService::class
}

