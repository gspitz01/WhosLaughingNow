package com.gregspitz.whoslaughingnow.game

import android.support.annotation.VisibleForTesting
import com.gregspitz.whoslaughingnow.UseCase
import com.gregspitz.whoslaughingnow.UseCaseHandler
import com.gregspitz.whoslaughingnow.data.model.Game
import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.game.domain.usecase.GetNewGame

/**
 * Presenter for a GameContract.View
 */
class GamePresenter(private val useCaseHandler: UseCaseHandler,
                    private val gameView: GameContract.View,
                    private val getNewGame: GetNewGame) : GameContract.Presenter {

    private var game: Game? = null

    init {
        gameView.setPresenter(this)
    }

    override fun loadNewGame() {
        gameView.setLoadingIndicator(true)

        useCaseHandler.execute(getNewGame, GetNewGame.RequestValues(),
                object: UseCase.UseCaseCallback<GetNewGame.ResponseValue> {
                    override fun onSuccess(response: GetNewGame.ResponseValue) {
                        game = response.game
                        val laughers =
                                mutableListOf(response.game.correctAnswer)
                        laughers.addAll(response.game.wrongAnswers)
                        if (gameView.isActive()) {
                            gameView.setLoadingIndicator(false)
                            gameView.setLaughFile(response.game.correctAnswer.fileName)
                            gameView.showLaughers(laughers.shuffled())
                        }
                    }

                    override fun onError() {
                        if (gameView.isActive()) {
                            gameView.setLoadingIndicator(false)
                            gameView.showFailedToLoadGame()
                        }
                    }
                })
    }

    override fun makeGuess(laugher: Laugher) {
        game?.let {
            if (it.correctAnswer == laugher) {
                gameView.showRightAnswer()
            } else {
                gameView.showWrongAnswer()
            }
        }
    }

    override fun start() {
        loadNewGame()
    }

    @VisibleForTesting
    fun getGame() = game
}
