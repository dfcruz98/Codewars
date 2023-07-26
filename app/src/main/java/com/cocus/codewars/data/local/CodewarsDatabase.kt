package com.cocus.codewars.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cocus.codewars.data.local.entities.Challenge

const val DATABASE_NAME = "Codewars"

@Database(entities = [Challenge::class], version = 1)
abstract class CodewarsDatabase : RoomDatabase() {
    //abstract fun companyDao(): CompanyDao
}