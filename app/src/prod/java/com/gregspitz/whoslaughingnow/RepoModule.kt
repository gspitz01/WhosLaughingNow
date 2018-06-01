package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.source.GameRepository
import com.gregspitz.whoslaughingnow.data.source.local.GameInMemoryLocalDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {

    @Provides @Singleton
    fun provideGameLocalDataSource(): GameInMemoryLocalDataSource {
        return GameInMemoryLocalDataSource()
    }

    @Provides @Singleton
    fun provideGameRepository(gameLocalDataSource: GameInMemoryLocalDataSource): GameRepository {
        return GameRepository(gameLocalDataSource)
    }
}
