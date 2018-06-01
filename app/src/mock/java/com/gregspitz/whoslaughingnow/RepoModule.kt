package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.source.GameRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Dagger module for mock repo
 */
@Module
class RepoModule {

    @Provides @Singleton
    fun provideLocalDataSource(): FakeGameLocalDataSource {
        return FakeGameLocalDataSource()
    }

    @Provides @Singleton
    fun provideRepository(gameLocalDataSource: FakeGameLocalDataSource): GameRepository {
        return GameRepository(gameLocalDataSource)
    }
}
