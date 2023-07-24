package uz.gita.a5.mymemorygame.domain.repository

import uz.gita.a5.mymemorygame.R
import uz.gita.a5.mymemorygame.data.CardData

class AppRepository private constructor() {

    private val cardList = ArrayList<CardData>()
    private var oldCardList = ArrayList<CardData>()

    companion object {
        private lateinit var instance: AppRepository
        fun getInstance(): AppRepository {
            if (!(::instance.isInitialized)) {
                instance = AppRepository()
            }
            return instance
        }
    }


    init {
        cardList.add(CardData(R.drawable.image_1, 1))
        cardList.add(CardData(R.drawable.image_2, 2))
        cardList.add(CardData(R.drawable.image_3, 3))
        cardList.add(CardData(R.drawable.imag_4, 4))
        cardList.add(CardData(R.drawable.imag_5, 5))
        cardList.add(CardData(R.drawable.imag_6, 6))
        cardList.add(CardData(R.drawable.imag_7, 7))
        cardList.add(CardData(R.drawable.imag_8, 8))
        cardList.add(CardData(R.drawable.imag_9, 9))
        cardList.add(CardData(R.drawable.imag_10, 10))
        cardList.add(CardData(R.drawable.imag_11, 11))
        cardList.add(CardData(R.drawable.imag_12, 12))
        cardList.add(CardData(R.drawable.imag_13, 13))
        cardList.add(CardData(R.drawable.imag_14, 14))
        cardList.add(CardData(R.drawable.imag_15, 15))
        cardList.add(CardData(R.drawable.imag_16, 16))
        cardList.add(CardData(R.drawable.imag_17, 17))
        cardList.add(CardData(R.drawable.imag_18, 18))
        cardList.add(CardData(R.drawable.img_19, 19))
        cardList.add(CardData(R.drawable.imag_20, 20))
        cardList.add(CardData(R.drawable.imag_21, 21))
        cardList.add(CardData(R.drawable.imag_22, 22))
        cardList.add(CardData(R.drawable.imag_23, 23))
        cardList.add(CardData(R.drawable.imag_24, 24))
    }
    /*init {
        cardList.add(CardData(R.drawable.image_1, 1))
        cardList.add(CardData(R.drawable.image_2, 2))
        cardList.add(CardData(R.drawable.image_3, 3))
        cardList.add(CardData(R.drawable.imag_4, 4))
        cardList.add(CardData(R.drawable.imag_5, 5))
        cardList.add(CardData(R.drawable.imag_6, 6))
        cardList.add(CardData(R.drawable.imag_7, 7))
        cardList.add(CardData(R.drawable.imag_8, 8))
        cardList.add(CardData(R.drawable.imag_9, 9))
        cardList.add(CardData(R.drawable.imag_10, 10))
        cardList.add(CardData(R.drawable.imag_11, 11))
        cardList.add(CardData(R.drawable.imag_12, 12))
        cardList.add(CardData(R.drawable.image_1, 13))
        cardList.add(CardData(R.drawable.image_2, 14))
        cardList.add(CardData(R.drawable.image_3, 15))
        cardList.add(CardData(R.drawable.imag_4, 16))
        cardList.add(CardData(R.drawable.imag_5, 17))
        cardList.add(CardData(R.drawable.imag_6, 18))
        cardList.add(CardData(R.drawable.imag_7, 19))
        cardList.add(CardData(R.drawable.imag_8, 20))
        cardList.add(CardData(R.drawable.imag_9, 21))
        cardList.add(CardData(R.drawable.imag_10, 22))
        cardList.add(CardData(R.drawable.imag_11, 23))
        cardList.add(CardData(R.drawable.imag_12, 24))

    }*/

    fun getData(count: Int): List<CardData> {
        cardList.shuffle()
        var ls = cardList.subList(0, count / 2)
        var result = ArrayList<CardData>(ls)
        result.addAll(ls)

        result.shuffle()
        oldCardList = result
        return result
    }

    fun getOldData() = oldCardList
}