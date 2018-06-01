package com.gregspitz.whoslaughingnow.data.source

class GameRepository(private val localDataSource: GameDataSource) : GameDataSource {

    // TODO: test and implement this

    override fun getLaughers(callback: GameDataSource.GetLaughersCallback) {
        localDataSource.getLaughers(callback)
    }
}
