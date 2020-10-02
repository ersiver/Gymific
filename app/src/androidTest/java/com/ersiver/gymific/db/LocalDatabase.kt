package com.ersiver.gymific.db

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
abstract class LocalDatabase {
    lateinit var database: GymificDatabase

    @Before
    fun setUp() {
        database =
            Room.inMemoryDatabaseBuilder(
                getApplicationContext(),
                GymificDatabase::class.java
            )
                .allowMainThreadQueries()
                .build()
    }

    @After
    fun tearDown() {
        database.close()
    }
}