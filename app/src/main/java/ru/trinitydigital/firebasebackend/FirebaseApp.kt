package ru.trinitydigital.firebasebackend

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ru.trinitydigital.firebasebackend.di.appModules

class FirebaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FirebaseApp)
            modules(appModules)
        }
    }
}