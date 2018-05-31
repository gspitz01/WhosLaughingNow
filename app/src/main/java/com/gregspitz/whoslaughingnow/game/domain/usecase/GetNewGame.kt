package com.gregspitz.whoslaughingnow.game.domain.usecase

import com.gregspitz.whoslaughingnow.UseCase
import com.gregspitz.whoslaughingnow.data.model.Game
import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.data.source.GameDataSource
import com.gregspitz.whoslaughingnow.data.source.GameRepository
import java.util.*

/**
 * Use case to get a new game of Who's Laughing Now
 */
class GetNewGame(private val repository: GameRepository)
    : UseCase<GetNewGame.RequestValues, GetNewGame.ResponseValue>() {

    override fun executeUseCase(requestValues: RequestValues) {
        repository.getLaughers(object: GameDataSource.GetLaughersCallback {
            override fun onLaughersLoaded(laughers: List<Laugher>) {
                // If there aren't enough Laughers for a full game (must be at least four)
                // call callback with error
                if (laughers.size < 4) {
                    getUseCaseCallback().onError()
                } else {
                    val correctAnswer = laughers.getRandomElement()
                    val wrongAnswers = laughers
                            .filter { it != correctAnswer }
                            .getRandomElements(3)
                    if (wrongAnswers == null || wrongAnswers.size < 3) {
                        getUseCaseCallback().onError()
                    } else {
                        getUseCaseCallback().onSuccess(ResponseValue(Game(correctAnswer, wrongAnswers)))
                    }
                }
            }

            override fun onDataNotAvailable() {
                getUseCaseCallback().onError()
            }
        })
    }

    class RequestValues() : UseCase.RequestValues

    class ResponseValue(val game: Game) : UseCase.ResponseValue
}


/**
 * Extension functions for list to get a single random value and a list of several
 * Both borrowed from https://www.rosettacode.org/wiki/Pick_random_element#Kotlin
 */
fun <E> List<E>.getRandomElement() = this[Random().nextInt(this.size)]

fun <E> List<E>.getRandomElements(numberOfElements: Int): List<E>? {
    if (numberOfElements > this.size) {
        return null
    }
    return this.shuffled().take(numberOfElements)
}
