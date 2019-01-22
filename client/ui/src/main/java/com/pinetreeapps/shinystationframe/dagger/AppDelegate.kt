package com.pinetreeapps.shinystationframe.dagger

import android.app.Application
import net.danlew.android.joda.JodaTimeAndroid
import org.joda.time.DateTimeZone
import java.util.*

class AppDelegate : Application() {
    companion object {
        //platformStatic allow access it from java code
        @JvmStatic
        lateinit var graph: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        graph = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        graph.inject(this)

        JodaTimeAndroid.init(this)
        DateTimeZone.setDefault(DateTimeZone.forTimeZone(TimeZone.getDefault()))
    }
}