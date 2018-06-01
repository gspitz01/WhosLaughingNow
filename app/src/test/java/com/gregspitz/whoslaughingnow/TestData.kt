package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.model.Game
import com.gregspitz.whoslaughingnow.data.model.Laugher

object TestData {

    val LAUGHERS = listOf(Laugher("0", "Bob", "bob"),
            Laugher("1", "Tina", "tina"),
            Laugher("2", "Isaac", "isaac"),
            Laugher("3", "Sven", "sven"))

    val GAME = Game(LAUGHERS[0], LAUGHERS.takeLast(3))
}
