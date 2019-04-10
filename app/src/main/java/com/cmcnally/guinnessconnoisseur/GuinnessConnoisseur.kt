package com.cmcnally.guinnessconnoisseur

import android.app.Application
import org.koin.android.ext.android.startKoin

//Application class needed for dependency injection
class GuinnessConnoisseur: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koin))
    }
}