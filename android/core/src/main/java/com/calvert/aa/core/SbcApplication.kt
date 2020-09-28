package com.calvert.aa.core

import android.app.Application
import com.calvert.aa.core.di.module.coreModule
import com.calvert.aa.core.di.module.retrofitModule
import io.palaima.debugdrawer.timber.data.LumberYard
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class AbtApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            val lumberYard: LumberYard = LumberYard.getInstance(this)
            lumberYard.cleanUp()

            Timber.plant(lumberYard.tree())
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@AbtApplication)
            modules(listOf(retrofitModule, coreModule))
        }
    }
}