package com.akosivis.androiddevelopertechnicalchallenge.database

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Reviews (
    var rating        : Int?    = null,
    var comment       : String? = null,
    var date          : String? = null,
    var reviewerName  : String? = null,
    var reviewerEmail : String? = null
)