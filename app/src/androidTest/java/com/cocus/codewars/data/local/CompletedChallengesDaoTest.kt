package com.cocus.codewars.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cocus.codewars.data.local.dao.CompletedChallengesDao
import com.cocus.codewars.data.local.entities.CompletedChallengeEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CompletedChallengesDaoTest {

    private lateinit var dao: CompletedChallengesDao
    private lateinit var db: CodewarsDatabase

    private val baseEntity = CompletedChallengeEntity(
        id = "514b92a657cdc65150000007",
        user = "mike",
        name = "Multiples of 3 and 5",
        slug = "multiples-of-3-and-5",
        completedAt = "2017-04-06T16:32:09Z",
        completedLanguages = listOf("Javascript", "Kotlin", "C", "Rust")
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CodewarsDatabase::class.java
        ).build()
        dao = db.completedChallengesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `get challenge by id`() = runTest {
        dao.insert(listOf(baseEntity))
        val page = dao.get(baseEntity.id)
        assertThat(page).isNotNull()
    }

    @Test
    fun `delete all`() = runTest {
        dao.insert(listOf(baseEntity))
        dao.deleteAll()
        val page = dao.get(baseEntity.id)
        assertThat(page).isNull()
    }

}