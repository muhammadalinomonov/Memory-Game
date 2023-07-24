package uz.gita.a5.mymemorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a5.mymemorygame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        setContentView(R.layout.activity_main)
    }
}