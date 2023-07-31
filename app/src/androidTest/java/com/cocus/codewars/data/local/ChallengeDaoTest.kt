package com.cocus.codewars.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cocus.codewars.data.local.dao.ChallengeDao
import com.cocus.codewars.data.local.entities.ApprovedByEntity
import com.cocus.codewars.data.local.entities.ChallengeEntity
import com.cocus.codewars.data.local.entities.CreatedByEntity
import com.cocus.codewars.data.local.entities.RankEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class ChallengeDaoTest {

    private lateinit var challengeDao: ChallengeDao
    private lateinit var db: CodewarsDatabase

    private val baseEntity = ChallengeEntity(
        id = "5277c8a221e209d3f6000b56",
        name = "Valid Braces",
        slug = "valid-braces",
        url = "http://www.codewars.com/kata/valid-braces",
        category = "algorithms",
        description = "Write a function called `validBraces` that takes a string ...",
        tags = listOf("Algorithms", "Validation", "Logic", "Utilities"),
        languages = listOf("javascript", "coffeescript"),
        rank = RankEntity(
            id = -4,
            name = "4 kyu",
            color = "blue",
        ),
        createdBy = CreatedByEntity(
            username = "xDranik",
            url = "http://www.codewars.com/users/xDranik",
        ),
        approvedBy = ApprovedByEntity(
            username = "xDranik",
            url = "http://www.codewars.com/users/xDranik",
        ),
        totalAttempts = 4911,
        totalCompleted = 919,
        totalStars = 12,
        voteScore = 512,
        publishedAt = "2013-11-05T00:07:31Z",
        approvedAt = "013-12-20T14:53:06Z",
    )

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CodewarsDatabase::class.java
        ).build()
        challengeDao = db.challengeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `insert challenge`() = runBlocking {
        challengeDao.insert(baseEntity)
        val result = challengeDao.get(baseEntity.id).first()
        assertThat(result).isEqualTo(baseEntity)
    }

    @Test
    fun `update challenge`() = runTest {
        challengeDao.insert(baseEntity)
        val update = baseEntity.copy(name = "Valid Parenthesis")
        challengeDao.update(update)
        val result = challengeDao.get(baseEntity.id).first()
        assertThat(result).isEqualTo(update)
    }

    @Test
    fun `delete challenge`() = runTest {
        challengeDao.insert(baseEntity)
        challengeDao.delete(baseEntity)
        val result = challengeDao.get(baseEntity.id).first()
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `clear all`() = runTest {
        challengeDao.insert(baseEntity)
        challengeDao.clearAll()
        val challenges = challengeDao.getAll().first()
        assertThat(challenges).isEqualTo(null)
    }
}