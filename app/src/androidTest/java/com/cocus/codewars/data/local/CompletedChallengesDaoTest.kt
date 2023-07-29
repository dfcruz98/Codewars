package com.cocus.codewars.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cocus.codewars.data.local.dao.CompletedChallengesDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CompletedChallengesDaoTest {

    private lateinit var completedChallengesDao: CompletedChallengesDao
    private lateinit var db: CodewarsDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CodewarsDatabase::class.java
        ).build()
        completedChallengesDao = db.completedChallengesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

}