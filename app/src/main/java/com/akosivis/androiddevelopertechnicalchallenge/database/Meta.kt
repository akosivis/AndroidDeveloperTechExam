package com.akosivis.androiddevelopertechnicalchallenge.database

import androidx.room.Entity
import kotlinx.serialization.Serializable

@Entity
@Serializable
data class Meta (
    var createdAt : String? = null,
    var updatedAt : String? = null,
    var barcode   : String? = null,
    var qrCode    : String? = null
)