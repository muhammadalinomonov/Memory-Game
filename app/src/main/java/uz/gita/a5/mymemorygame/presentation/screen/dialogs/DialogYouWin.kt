package uz.gita.a5.mymemorygame.presentation.screen.dialogs

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.a5.mymemorygame.R
import uz.gita.a5.mymemorygame.databinding.DialogYouWinBinding

class DialogYouWin:DialogFragment(R.layout.dialog_you_win) {
    private val binding by viewBinding(DialogYouWinBinding::bind)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}