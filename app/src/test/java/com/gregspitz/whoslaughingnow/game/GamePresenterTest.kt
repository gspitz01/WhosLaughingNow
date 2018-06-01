package com.gregspitz.whoslaughingnow.game

import com.gregspitz.whoslaughingnow.TestData.GAME
import com.gregspitz.whoslaughingnow.UseCase
import com.gregspitz.whoslaughingnow.UseCaseHandler
import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.game.domain.usecase.GetNewGame
import com.nhaarman.mockito_kotlin.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests for {@link GamePresenter}
 */
class GamePresenterTest {

    private val getNewGame: GetNewGame = mock()

    private val requestCaptor = argumentCaptor<GetNewGame.RequestValues>()

    private val getNewGameCallbackCaptor =
            argumentCaptor<UseCase.UseCaseCallback<GetNewGame.ResponseValue>>()

    private val response = GetNewGame.ResponseValue(GAME)

    private val useCaseHandler: UseCaseHandler = mock()

    private val gameView: GameContract.View = mock()

    private val inOrder = inOrder(gameView)

    private val showLaughersCaptor = argumentCaptor<List<Laugher>>()

    private lateinit var gamePresenter: GamePresenter

    @Before
    fun setup() {
        whenever(gameView.isActive()).thenReturn(true)
        gamePresenter = GamePresenter(useCaseHandler, gameView, getNewGame)
    }

    @Test
    fun onConstruction_setsSelfOnView() {
        verify(gameView).setPresenter(gamePresenter)
    }

    @Test
    fun onLoadNewGame_successFromUseCase_callsShowLaughersOnView() {
        gamePresenter.loadNewGame()
        verifyLoadNewGameSuccess()
    }

    @Test
    fun onLoadNewGame_failureFromUseCase_callsFailedToLoadOnView() {
        gamePresenter.loadNewGame()

        verifySetLoadingIndicator(true)

        verifyUseCaseCalled()
        getNewGameCallbackCaptor.firstValue.onError()

        verifySetLoadingIndicator(false)
        verify(gameView).showFailedToLoadGame()
    }

    @Test
    fun onStart_callsLoadNewGame() {
        gamePresenter.start()
        verifyLoadNewGameSuccess()
    }

    @Test
    fun onMakeGuess_correctGuess_callsShowRightAnswerOnView() {
        gamePresenter.loadNewGame()
        verifyLoadNewGameSuccess()
        gamePresenter.makeGuess(GAME.correctAnswer)
        verify(gameView).showRightAnswer()
    }

    @Test
    fun onMakeGuess_incorrectGuess_callsShowWrongAnswerOnView() {
        gamePresenter.loadNewGame()
        verifyLoadNewGameSuccess()
        gamePresenter.makeGuess(GAME.wrongAnswers[0])
        verify(gameView).showWrongAnswer()
    }

    @Test
    fun onMakeGuessBeforeLoadNewGame_doesNothing() {
        verify(gameView).setPresenter(gamePresenter)
        gamePresenter.makeGuess(GAME.correctAnswer)
        verifyNoMoreInteractions(gameView)
    }

    private fun verifyLoadNewGameSuccess() {
        verifySetLoadingIndicator(true)

        verifyUseCaseCalled()
        getNewGameCallbackCaptor.firstValue.onSuccess(response)

        verifySetLoadingIndicator(false)
        verify(gameView).showLaughers(showLaughersCaptor.capture())
        val laughers = showLaughersCaptor.firstValue

        assertEquals(4, laughers.size)
        assertTrue(laughers.contains(GAME.correctAnswer))
        assertTrue(laughers.containsAll(GAME.wrongAnswers))
    }

    private fun verifySetLoadingIndicator(active: Boolean) {
        inOrder.verify(gameView).setLoadingIndicator(active)
    }

    private fun verifyUseCaseCalled() {
        verify(useCaseHandler).execute(eq(getNewGame), requestCaptor.capture(),
                getNewGameCallbackCaptor.capture())
    }

}
