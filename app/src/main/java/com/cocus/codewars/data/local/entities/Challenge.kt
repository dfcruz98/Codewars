package com.cocus.codewars.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Challenge(
    @PrimaryKey
    val id: Long
)