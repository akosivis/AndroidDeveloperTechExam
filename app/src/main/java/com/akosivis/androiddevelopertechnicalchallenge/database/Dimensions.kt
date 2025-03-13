package com.akosivis.androiddevelopertechnicalchallenge.database

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Dimensions (
    var width  : Double? = null,
    var height : Double? = null,
    var depth  : Double? = null
)