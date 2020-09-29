package com.ersiver.gymific.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "workout_table")
class Workout(
    @PrimaryKey
    val id: Int,
    val title: String,
    val time: Long,
    val category: String,
    val iconCode: String,
    val instruction: String,
    var isSaved: Boolean,
    var timeSaved: Long,
    var isRecommended: Boolean
) : Parcelable