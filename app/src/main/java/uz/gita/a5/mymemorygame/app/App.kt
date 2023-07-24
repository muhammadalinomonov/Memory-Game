package uz.gita.a5.mymemorygame.app

import android.app.Application
import uz.gita.a5.mymemorygame.settings.MyMusicPlayer
import uz.gita.a5.mymemorygame.settings.MyPref

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        MyPref.init(this)
        MyMusicPlayer.init(this)
    }
}