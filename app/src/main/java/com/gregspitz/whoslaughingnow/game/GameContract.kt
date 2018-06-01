package com.gregspitz.whoslaughingnow.game

import com.gregspitz.whoslaughingnow.BasePresenter
import com.gregspitz.whoslaughingnow.BaseView
import com.gregspitz.whoslaughingnow.data.model.Game
import com.gregspitz.whoslaughingnow.data.model.Laugher


/**
 * Contract between a GameView and its presenter
 */
interface GameContract {

    interface View : BaseView<Presenter> {

        fun setLoadingIndicator(active: Boolean)

        fun setLaughFile(laughFileName: String)

        fun showLaughers(laughers: List<Laugher>)

        fun showFailedToLoadGame()

        /**
         * Tell the view to show that a guess was correct
         */
        fun showRightAnswer()

        /**
         * Tell the view to show that a guess was correct
         */
        fun showWrongAnswer()

        fun isActive(): Boolean
    }

    interface Presenter : BasePresenter {

        fun loadNewGame()

        fun makeGuess(laugher: Laugher)
    }
}
