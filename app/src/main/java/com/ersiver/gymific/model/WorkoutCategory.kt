package com.ersiver.gymific.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class WorkoutCategory(
    @PrimaryKey
    val id: String,
    val title: String,
    val overview: String,
    val description: String,
)