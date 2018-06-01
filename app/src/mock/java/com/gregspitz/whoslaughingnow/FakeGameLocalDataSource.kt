package com.gregspitz.whoslaughingnow

import com.gregspitz.whoslaughingnow.data.model.Laugher
import com.gregspitz.whoslaughingnow.data.source.GameDataSource

class FakeGameLocalDataSource : GameDataSource {

    private val database = mutableMapOf<String, Laugher>()

    override fun getLaughers(callback: GameDataSource.GetLaughersCallback) {
        if (database.isNotEmpty()) {
            callback.onLaughersLoaded(database.values.toList())
        } else {
            callback.onDataNotAvailable()
        }
    }

    fun addLaugher(laugher: Laugher) {
        database[laugher.id] = laugher
    }

    fun addLaughers(laughers: List<Laugher>) {
        for (laugher in laughers) {
            database[laugher.id] = laugher
        }
    }

    fun clear() {
        database.clear()
    }
}
