package io.github.sergeyboboshko.usecomposeentityexample

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication1 : Application() {
    // Можна додати додаткову логіку тут, якщо потрібно
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext  // Ініціалізуємо глобальний Application Context
    }
}
