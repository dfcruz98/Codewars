package com.cocus.codewars.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cocus.codewars.data.local.dao.CompletedChallengesPagesDao
import com.cocus.codewars.data.local.entities.CompletedChallengePageEntity
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
class CompletedChallengesPagesDaoTest {

    private lateinit var dao: CompletedChallengesPagesDao
    private lateinit var db: CodewarsDatabase

    private val baseEntity = CompletedChallengePageEntity(
        id = 100,
        user = "mike",
        previousPage = null,
        nextPage = 1,
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CodewarsDatabase::class.java
        ).build()
        dao = db.completedChallengesPagesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertPage() = runTest {
        dao.insert(listOf(baseEntity))
        val page = dao.getByUser(baseEntity.user)
        assertThat(page.id).isEqualTo(baseEntity.id)
    }

    @Test
    fun getByIdPage() = runTest {
        dao.insert(listOf(baseEntity))
        val page = dao.getById(baseEntity.id)
        assertThat(page.id).isEqualTo(baseEntity.id)
    }

    @Test
    fun getByIdUser() = runTest {
        dao.insert(listOf(baseEntity))
        val page = dao.getByUser(baseEntity.user)
        assertThat(page.id).isEqualTo(baseEntity.id)
    }

    @Test
    fun  deleteAll() = runTest {
        dao.insert(listOf(baseEntity))
        dao.deleteAll()
        val page = dao.getById(baseEntity.id)
        assertThat(page).isEqualTo(null)
    }
}