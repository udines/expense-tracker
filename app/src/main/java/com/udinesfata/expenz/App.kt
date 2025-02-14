package com.udinesfata.expenz

import android.app.Application
import com.udinesfata.expenz.data.di.dataModule
import com.udinesfata.expenz.domain.di.domainModule
import com.udinesfata.expenz.ui.di.uiModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(dataModule, domainModule, uiModule)
        }
    }
}