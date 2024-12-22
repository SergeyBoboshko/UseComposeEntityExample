package io.github.sergeyboboshko.usecomposeentityexample

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import io.github.sergeyboboshko.composeentity.daemons.LocaleHelper
import io.github.sergeyboboshko.composeentity.daemons.localization.LocalizationManager

@HiltAndroidApp
class MyApplication1 : Application() {
    // Можна додати додаткову логіку тут, якщо потрібно
    companion object {
        lateinit var appContext: Context
            private set
    }

    override fun onCreate() {
        super.onCreate()
        //LocalizationManager.currentLanguageCode="en"
        //Звичайна локалізація
        //val updatedContext = LocaleHelper.setLocale(applicationContext, "en") // Встановлення локалі
        //updatedContext.assets.locales.set(0,"en")
        //applyOverrideConfiguration(updatedContext.resources.configuration)


        appContext = applicationContext  // Ініціалізуємо глобальний Application Context
        //appContext = updatedContext  // Ініціалізуємо глобальний Application Context
    }
}
