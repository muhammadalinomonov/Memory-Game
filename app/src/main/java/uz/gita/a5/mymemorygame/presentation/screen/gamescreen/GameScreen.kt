package uz.gita.a5.mymemorygame.presentation.screen.gamescreen

import android.animation.ObjectAnimator
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import uz.gita.a5.mymemorygame.R
import uz.gita.a5.mymemorygame.data.CardData
import uz.gita.a5.mymemorygame.data.LevelEnum
import uz.gita.a5.mymemorygame.databinding.ScreenGameBinding
import uz.gita.a5.mymemorygame.domain.repository.AppRepository
import uz.gita.a5.mymemorygame.settings.MyMusicPlayer
import uz.gita.a5.mymemorygame.settings.MyPref


class GameScreen : Fragment(R.layout.screen_game) {
    private var isStart = false

    private lateinit var player: MyMusicPlayer

    private var openedImgId = 0
    private lateinit var progressJob: Job
    private val binding by viewBinding(ScreenGameBinding::bind)
    private var defLevel = LevelEnum.EASY
    private val args by navArgs<GameScreenArgs>()
    private val repository = AppRepository.getInstance()
    private var _height = 0
    private var _wight = 0
    private var duration: Long = 0L
    private var firsImage: ImageView? = null
    private var secondImage: ImageView? = null
    private var i: Int = 0
    private val MAX_TIME by lazy {
        when (defLevel) {
            LevelEnum.EASY -> 100
            LevelEnum.MEDIUM -> 200
            LevelEnum.HARD -> 300
        }
    }
    private lateinit var job: Job
    private lateinit var pref: MyPref
    private var levelInt = 0
    private val images = ArrayList<ImageView>()
//    private lateinit var openedImg: ImageView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        player = MyMusicPlayer.getInstance()
        pref = MyPref.getInstance()

        if (pref.getMusicState()) {
            binding.menu.setImageResource(R.drawable.sound_on)
        } else {
            binding.menu.setImageResource(R.drawable.sound_off)
        }
        binding.menu.setOnClickListener {
            if (pref.getMusicState()) {
                player.manageMusic()
                binding.menu.setImageResource(R.drawable.sound_off)
            } else {
                player.manageMusic()
                binding.menu.setImageResource(R.drawable.sound_on)
            }
        }


        defLevel = args.level

        levelInt = pref.getLevel(defLevel)
        binding.level.text = levelInt.toString()
        i =
            if (defLevel == LevelEnum.EASY) 100
            else if (defLevel == LevelEnum.MEDIUM) 200
            else 300

        binding.time.text = i.toString()


        binding.homeButton.setOnClickListener {
            findNavController().navigateUp()
        }



        binding.space.post {
            _height = binding.container.height / defLevel.verCount
            _wight = binding.container.width / defLevel.horCount

            val count = defLevel.horCount * defLevel.verCount

            val ls = repository.getData(count)
            describeCardData(ls)
        }
        progressJob = lifecycleScope.launch {


            val process = binding.horizontalProgressBar


            val animation = ObjectAnimator.ofInt(process, "progress", 0, i)
            animation.duration = 2000L
            animation.interpolator = DecelerateInterpolator()
            animation.start()
            Log.d("TTT", "sss")
            if (i == 300)
                delay(5900)
            else
                delay(4000L)
            isStart = true

            val progressBar = binding.horizontalProgressBar
            progressBar.max = i
            job = lifecycleScope.launch {
                while (i > 0) {
                    progressBar.progress = i
                    i--
                    binding.time.text = i.toString()
                    if (i == 0) {
                        Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_SHORT).show()
                        showGameOverDialog()
                    }
                    delay(1000L)
                }
            }
        }
    }

    private fun describeCardData(ls: List<CardData>) {
        Log.d("CCC", binding.container.childCount.toString())
        for (i in 0 until binding.container.childCount) {
            binding.container.removeView(binding.container.getChildAt(i))
        }
        Log.d("CCC", binding.container.childCount.toString())
        duration = 0L
        lifecycleScope.launch {
            for (i in 0 until defLevel.horCount) {
                for (j in 0 until defLevel.verCount) {

                    duration = 250L * i + 100 * j
                    val image = ImageView(requireContext())
                    binding.container.addView(image)
                    val lp = image.layoutParams as ConstraintLayout.LayoutParams

                    lp.apply {
                        width = _wight
                        height = _height
                    }
                    lp.setMargins(4, 4, 4, 4)
                    image.layoutParams = lp
                    image.tag = ls[i * defLevel.verCount + j]
                    image.setImageResource(R.drawable.img_bg)
//                image.setImageResource(ls[i].imgRes)
//                image.scaleType = ImageView.ScaleType.CENTER_CROP
                    image.animate()
                        .x(i * _wight * 1f)
                        .y(j * _height * 0.85f)
                        .setDuration(duration)
                        .withEndAction {

                        }
                        .start()
                    images.add(image)
                }
            }
            delay(duration)
            openFirst(images)
        }



        addClickListener()
    }

    private fun openFirst(images: ArrayList<ImageView>) {
        images.forEach { imageView ->
            val data = imageView.tag as CardData

            imageView.animate()
                .setDuration(250)
                .rotationY(90f)
                .setInterpolator(AccelerateInterpolator())
                .withEndAction {

                    imageView.setImageResource(data.imgRes)
                    imageView.rotationY = -90f
                    imageView.animate()
                        .setDuration(700)
                        .rotationY(0f)
                        .setInterpolator(DecelerateInterpolator())
                        .withEndAction {
                            lifecycleScope.launch {
                                delay(i * 5L)
                                imageView.animate()
                                    .setInterpolator(AccelerateInterpolator())
                                    .setDuration(500)
                                    .rotationY(90f)
                                    .withEndAction {

                                        imageView.setImageResource(R.drawable.img_bg)
                                        imageView.rotationY = -89.9f
                                        imageView.animate()
                                            .setInterpolator(DecelerateInterpolator())
                                            .setDuration(350)
                                            .rotationY(0f)
                                            .withEndAction {

                                            }
                                            .start()
                                    }
                                    .start()
                            }
                                .start()
                        }
                        .start()

                }
        }
    }

    private fun addClickListener() {

        var s = ""
        images.forEach { image ->
            s += (image.tag as CardData).id.toString() + "   "
        }
        Log.d("SSS", s)
        images.forEach { imageView ->
            Log.d("SSS", "${((imageView.tag) as CardData).id}")
            imageView.setOnClickListener {
                if (firsImage == null && isStart) {
                    firsImage = imageView
                    player.startOpenCardMusic()
                    openFirstCard(imageView)
                    imageView.isClickable = false
                } else if (secondImage == null && isStart) {
//                    player.stopOpenCardMusic()
                    Log.d("MMM", "music")
                    player.startOpenCardMusic()
                    secondImage = imageView
                    openSecondCard(imageView)
                    imageView.isClickable = false
                }
            }

        }
    }


    fun openFirstCard(imageView: ImageView) {
        val data = imageView.tag as CardData
        openedImgId = data.id
        imageView.animate()
            .setDuration(300)
            .rotationY(89f)
            .withEndAction {
                imageView.setImageResource(data.imgRes)
                imageView.rotationY = -89f
                imageView.animate()
                    .setDuration(300)
                    .rotationY(0f)
                    .withEndAction {

                    }
                    .start()
            }
            .start()
    }

    fun openSecondCard(imageView: ImageView) {
        secondImage = imageView
        imageView.animate()
            .setDuration(300)
            .rotationY(89f)
            .withEndAction {
                val data = imageView.tag as CardData
                imageView.setImageResource(data.imgRes)
                imageView.rotationY = -89f
                imageView.animate()
                    .setDuration(300)
                    .rotationY(0f)
                    .withEndAction {
                        if (data.id == openedImgId) {
                            player.startRemoveCardMusic()
                            Log.d("TTT", "${data.id} vs ${openedImgId} 2")
                            imageView.animate()
                                .setDuration(300)
                                .rotation(360f)
                                .scaleX(0f)
                                .scaleY(0f)
                                .withEndAction {
                                    binding.container.removeView(imageView)
                                    binding.container.removeView(firsImage)
                                    firsImage = null
                                    secondImage = null
                                    if (binding.container.childCount == 0) {
                                        job.cancel()
                                        val dialog = Dialog(requireContext())
                                        dialog.setContentView(R.layout.dialog_you_win)
                                        dialog.setCancelable(false)
                                        dialog.findViewById<TextView>(R.id.youWin).text = "You win!"
                                        dialog.findViewById<ImageView>(R.id.restart)
                                            .setImageResource(R.drawable.next)
                                        dialog.findViewById<ImageView>(R.id.home_button)
                                            .setOnClickListener {
                                                dialog.dismiss()
                                                findNavController().navigateUp()
                                            }
                                        dialog.findViewById<ImageView>(R.id.restart)
                                            .setOnClickListener {
                                                dialog.dismiss()
                                                isWin()

                                            }
                                        dialog.getWindow()
                                            ?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                                        dialog.show()
                                    }
                                }
                                .start()
                            firsImage!!.animate()
                                .scaleX(0f)
                                .scaleY(0f)
                                .setDuration(300)
                                .rotation(360f)
                                .start()
                        } else {
                            player.startCloseCardMusic()
                            closeCard(firsImage!!)
                            closeCard(imageView)
                        }
                    }
                    .start()
            }
            .start()
    }

    fun closeCard(imageView: ImageView) {

        Log.d("SSS", imageView.tag.toString())
        imageView.animate()
            .setDuration(300)
            .rotationY(-89f)
            .withEndAction {

                imageView.setImageResource(R.drawable.img_bg)
                imageView.rotationY = 89f
                imageView.animate()
                    .setDuration(300)
                    .rotationY(0f)
                    .withEndAction {
                        firsImage = null
                        secondImage = null
                        imageView.isClickable = true
                        openedImgId = 0
                    }
                    .start()
            }
            .start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::job.isInitialized)
            job.cancel()

        pref.saveLevel(defLevel, levelInt)
    }

    fun isWin() {
        binding.time.text = MAX_TIME.toString()
        job.cancel()
        levelInt++

        binding.level.text = levelInt.toString()

        progressJob.cancel()

//        lifecycleScope.launch {

//            delay(1000)
        val process = binding.horizontalProgressBar


        lifecycleScope.launch {
//            delay(1000)
            val animation = ObjectAnimator.ofInt(process, "progress", 0, MAX_TIME)
            Log.d("PPP", MAX_TIME.toString())
            animation.duration = 2000L
            binding.horizontalProgressBar.progress = MAX_TIME
            animation.interpolator = DecelerateInterpolator()
            animation.start()
        }
        describeCardData(repository.getData(defLevel.horCount * defLevel.verCount))
        i = MAX_TIME
        binding.time.text = i.toString()
//            delay(2000)
        job = lifecycleScope.launch {
            delay(MAX_TIME * 10L)
            while (i > 0) {
                process.progress = i
                i--
                binding.time.text = i.toString()
                if (i == 0) {
                    images.forEach { image ->
                        binding.container.removeView(image)
                    }
                    Toast.makeText(requireContext(), "Game Over", Toast.LENGTH_SHORT).show()
                    showGameOverDialog()
                }
                delay(1000L)
            }
//            }

        }
    }

    private fun showGameOverDialog() {
        val dialog = Dialog(requireContext())

        dialog.setContentView(R.layout.dialog_you_win)

        dialog.setCancelable(false)

        dialog.findViewById<ImageView>(R.id.home_button).setOnClickListener {
            dialog.dismiss()
            findNavController().navigateUp()

        }
        dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.findViewById<ImageView>(R.id.restart).setOnClickListener {

            dialog.dismiss()
            images.forEach {
                binding.container.removeView(it)
            }
            levelInt--
            isWin()
        }

        dialog.show()
    }

}

