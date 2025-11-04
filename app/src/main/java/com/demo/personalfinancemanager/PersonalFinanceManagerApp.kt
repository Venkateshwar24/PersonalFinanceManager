package com.demo.personalfinancemanager

import android.app.Application
import com.demo.personalfinancemanager.di.AppComponent
import com.demo.personalfinancemanager.di.DaggerAppComponent

/**
 * Application class to initialize the Dagger AppComponent.
 */
class PersonalFinanceManagerApp : Application() {

    lateinit var appComponent: AppComponent
        private set

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}
