package com.gregspitz.whoslaughingnow.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * Immutable class representing a person who has laughed, at least once
 */
@Entity(tableName = "laugher")
data class Laugher(@PrimaryKey val id: String = UUID.randomUUID().toString(),
                   val name: String)
