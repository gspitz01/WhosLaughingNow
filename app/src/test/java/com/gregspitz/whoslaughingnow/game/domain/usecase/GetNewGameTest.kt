package com.gregspitz.whoslaughingnow.game.domain.usecase

import com.gregspitz.whoslaughingnow.TestData.LAUGHERS
import com.gregspitz.whoslaughingnow.TestUseCaseScheduler
import com.gregspitz.whoslaughingnow.UseCase
import com.gregspitz.whoslaughingnow.UseCaseHandler
import com.gregspitz.whoslaughingnow.data.source.GameDataSource
import com.gregspitz.whoslaughingnow.data.source.GameRepository
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Tests for {@link GetNewGame}
 */
class GetNewGameTest {

    private val gameRepository: GameRepository = mock()

    private val requestValues = GetNewGame.RequestValues()

    private val repositoryCallbackCaptor =
            argumentCaptor<GameDataSource.GetLaughersCallback>()

    private val useCaseHandler = UseCaseHandler(TestUseCaseScheduler())

    private val useCaseCallback:
            UseCase.UseCaseCallback<GetNewGame.ResponseValue> = mock()

    private val responseCaptor = argumentCaptor<GetNewGame.ResponseValue>()

    private lateinit var getNewGame: GetNewGame

    @Before
    fun setup() {
        getNewGame = GetNewGame(gameRepository)
        useCaseHandler.execute(getNewGame, requestValues, useCaseCallback)
        verify(gameRepository).getLaughers(repositoryCallbackCaptor.capture())
    }

    @Test
    fun onSuccessFromRepoWithEnoughLaughers_callsSuccessOnCallbackWithGame() {
        repositoryCallbackCaptor.firstValue.onLaughersLoaded(LAUGHERS)
        verify(useCaseCallback).onSuccess(responseCaptor.capture())
        val responseGame = responseCaptor.firstValue.game
        assertEquals(3, responseGame.wrongAnswers.size)
        assertTrue(LAUGHERS.contains(responseGame.correctAnswer))
        assertTrue(LAUGHERS.containsAll(responseGame.wrongAnswers))
        assertFalse(responseGame.wrongAnswers.contains(responseGame.correctAnswer))
    }

    @Test
    fun onSuccessFromRepoWithoutEnoughLaughers_callsErrorOnCallback() {
        repositoryCallbackCaptor.firstValue.onLaughersLoaded(LAUGHERS.take(3))
        verify(useCaseCallback).onError()
    }

    @Test
    fun onFailureFromRepo_callsErrorOnCallback() {
        repositoryCallbackCaptor.firstValue.onDataNotAvailable()
        verify(useCaseCallback).onError()
    }
}
