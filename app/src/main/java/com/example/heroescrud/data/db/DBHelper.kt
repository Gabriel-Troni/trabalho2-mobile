package com.example.heroescrud.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
        const val DATABASE_NAME = "Heroes.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "Hero"
        }

        override fun onCreate(db: SQLiteDatabase) {
            val createTable = """
            CREATE TABLE $TABLE_NAME (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                company TEXT NOT NULL,
                origin TEXT NOT NULL,
                power TEXT NOT NULL
            )
        """.trimIndent()
            db.execSQL(createTable)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }