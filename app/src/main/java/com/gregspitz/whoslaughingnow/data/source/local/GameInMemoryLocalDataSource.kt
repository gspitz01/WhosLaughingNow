package com.gregspitz.whoslaughingnow.data.source.local

import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.data.source.GameDataSource

class GameInMemoryLocalDataSource : GameDataSource {

    private val database = mutableMapOf<String, Laugher>()

    init {
        for (laugher in InitialData.LAUGHERS) {
            database[laugher.id] = laugher
        }
    }

    override fun getLaughers(callback: GameDataSource.GetLaughersCallback) {
        callback.onLaughersLoaded(database.values.toList())
    }
}
