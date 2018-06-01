package com.gregspitz.whoslaughingnow.di

import com.gregspitz.whoslaughingnow.UseCaseHandler
import com.gregspitz.whoslaughingnow.UseCaseScheduler
import com.gregspitz.whoslaughingnow.UseCaseThreadPoolScheduler
import com.gregspitz.whoslaughingnow.data.source.GameRepository
import com.gregspitz.whoslaughingnow.game.domain.usecase.GetNewGame
import dagger.Module
import dagger.Provides

/**
 * Dagger module for use case injection
 */
@Module
class UseCaseModule {

    @Provides
    fun provideUseCaseScheduler(): UseCaseScheduler {
        return UseCaseThreadPoolScheduler()
    }

    @Provides
    fun provideUseCaseHandler(useCaseScheduler: UseCaseScheduler): UseCaseHandler {
        return UseCaseHandler(useCaseScheduler)
    }

    @Provides
    fun provideGetNewGame(gameRepository: GameRepository): GetNewGame {
        return GetNewGame(gameRepository)
    }
}
