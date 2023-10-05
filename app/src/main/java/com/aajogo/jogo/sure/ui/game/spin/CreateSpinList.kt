package com.aajogo.jogo.sure.ui.game.spin

import com.aajogo.jogo.sure.R
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.FIRST_ID
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.ITEM_COUNT
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.SCROLL_POSITION
import com.aajogo.jogo.sure.ui.game.spin.GameFragment.Companion.SECOND_ID
import kotlin.random.Random

object CreateSpinList {

    private const val ICON1 = R.drawable.icon1
    private const val ICON2 = R.drawable.icon2
    private const val ICON3 = R.drawable.icon3
    private const val ICON4 = R.drawable.icon4
    private const val ICON5 = R.drawable.icon5
    private const val ICON6 = R.drawable.icon6
    private var winId = 0
    private var loseId1 = 0
    private var loseId2 = 0

    fun createList(isWin: Boolean, id: Int): List<Int> {
        val resultList = mutableListOf<Int>()
        for (i in 1..6) {
            val icons = mutableListOf(ICON1, ICON2, ICON3, ICON4, ICON5, ICON6)
            while (icons.isNotEmpty()) {
                val ind = Random.nextInt(0, icons.size)
                val icon = icons[ind]
                resultList.add(icon)
                icons.removeAt(ind)
            }
        }
        val icons = listOf(ICON1, ICON2, ICON3, ICON4, ICON5, ICON6)
        if (isWin) {
            if (id == FIRST_ID) winId = icons[Random.nextInt(0, icons.size)]
            if (id == SECOND_ID) {
                resultList[SCROLL_POSITION] = winId
            } else {
                resultList[ITEM_COUNT - SCROLL_POSITION] = winId
            }
        } else {
            when (id) {
                FIRST_ID -> loseId1 = Random.nextInt(0, icons.size)
                SECOND_ID -> {
                    loseId2 = getId(loseId1, icons.size)
                    resultList[SCROLL_POSITION] = icons[loseId2]
                }
                else -> {
                    resultList[ITEM_COUNT - SCROLL_POSITION] = icons[getId(loseId2, icons.size)]
                }
            }
        }
        return resultList
    }

    private fun getId(id: Int, size: Int): Int {
        return if (id == size - 1) id - Random.nextInt(0, size)
        else id + 1
    }
}