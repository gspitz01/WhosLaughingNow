package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.model.Game
import com.gregspitz.whoslaughingnow.data.model.Laugher

object AndroidTestData {

    val LAUGHERS = listOf(
            Laugher("0", "Lizzy Caplan", "lizzy_caplan"),
            Laugher("1", "Nick Kroll", "nick_kroll"),
            Laugher("2", "Paul F. Tompkins", "paul_f_tompkins"),
            Laugher("3", "Scott Aukerman", "scott_aukerman"))

    val GAME = Game(LAUGHERS[0], LAUGHERS.takeLast(3))
}
