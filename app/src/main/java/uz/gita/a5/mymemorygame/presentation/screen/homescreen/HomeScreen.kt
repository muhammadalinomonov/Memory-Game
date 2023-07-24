package uz.gita.a5.mymemorygame.presentation.screen.homescreen

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a5.mymemorygame.R
import uz.gita.a5.mymemorygame.data.LevelEnum
import uz.gita.a5.mymemorygame.databinding.ScreenHomeBinding

class HomeScreen : Fragment(R.layout.screen_home) {

    private val binding by viewBinding(ScreenHomeBinding::bind)
    private var clickItem: ImageView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        clickItem = null

        binding.easy.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.easy
                binding.easy.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.easy.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LevelEnum.EASY)
                            }
                            .start()
                    }
                    .start()
            }
        }

        binding.medium.setOnClickListener {

            if (clickItem == null) {
                clickItem = binding.medium
                binding.medium.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.medium.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LevelEnum.MEDIUM)
                                clickItem = binding.medium
                            }
                            .start()
                    }
                    .start()
            }

        }


        binding.hard.setOnClickListener {
            if (clickItem == null) {
                clickItem = binding.hard
                binding.hard.animate()
                    .scaleX(0.7f)
                    .setDuration(200)
                    .scaleY(0.7f)
                    .withEndAction {
                        binding.hard.animate()
                            .setDuration(90)
                            .scaleY(1f)
                            .scaleX(1f)
                            .withEndAction {
                                openGameScreen(LevelEnum.HARD)
                                clickItem = binding.hard
                            }
                            .start()
                    }
                    .start()
            }
        }
    }

    private fun openGameScreen(level: LevelEnum) {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToGameScreen(level))
    }
}