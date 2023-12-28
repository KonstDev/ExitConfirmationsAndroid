package dem.llc.exitconfirmationsandroid.app

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)

        appContext = applicationContext
    }

    companion object{
        lateinit var appContext: Context
    }
}