package com.gregspitz.whoslaughingnow.data.source

import com.gregspitz.whoslaughingnow.data.model.Laugher

interface GameDataSource {

    /**
     * Interface for callbacks into getLaughers()
     */
    interface GetLaughersCallback {

        fun onLaughersLoaded(laughers: List<Laugher>)

        fun onDataNotAvailable()

    }

    /**
     * Get all available Laughers
     * @param callback to handle success or failure
     * @return all the Laughers from this source
     */
    fun getLaughers(callback: GetLaughersCallback)
}
