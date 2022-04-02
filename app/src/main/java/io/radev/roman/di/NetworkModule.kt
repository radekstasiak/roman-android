package io.radev.roman.di

import io.radev.roman.R
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
        io.radev.shared.network.ApiClient(
            apiKey = androidContext().resources.getString(R.string.FOURSQUARE_API_KEY)
        )
    }
    single { io.radev.shared.network.PlacesServiceImpl(get()) } bind io.radev.shared.network.PlacesService::class
}

