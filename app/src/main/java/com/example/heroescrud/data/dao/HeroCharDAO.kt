package com.example.heroescrud.data.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import androidx.core.content.contentValuesOf
import com.example.heroescrud.data.db.DBHelper
import com.example.heroescrud.model.HeroChar

class HeroCharDAO (private val context: Context) {
    private val dbHelper = DBHelper(context)

    fun addHeroChar(heroChar: HeroChar): Long {
        val db = dbHelper.writableDatabase
        val values = contentValuesOf().apply {
            put("name", heroChar.name)
            put("company", heroChar.company)
            put("origin", heroChar.origin)
            put("power", heroChar.power)
        }
        val id = db.insert(DBHelper.TABLE_NAME, null, values)
        db.close()
        return id
    }

    fun getAllHeroChars(): List<HeroChar> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, null, null, null,null, null)
        val charList = mutableListOf<HeroChar>()
        while(cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val company = cursor.getString(cursor.getColumnIndexOrThrow("company"))
            val origin = cursor.getString(cursor.getColumnIndexOrThrow("origin"))
            val power = cursor.getString(cursor.getColumnIndexOrThrow("power"))
            charList.add(HeroChar(id, name, company, origin, power))
        }
        cursor.close()
        db.close()
        return charList
    }

    fun getHeroById(id: Int): HeroChar? {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, null, "id = ?",arrayOf(id.toString()), null, null, null)

        var heroChar: HeroChar? = null
        if (cursor.moveToFirst()) {
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val company = cursor.getString(cursor.getColumnIndexOrThrow("company"))
            val origin = cursor.getString(cursor.getColumnIndexOrThrow("origin"))
            val power = cursor.getString(cursor.getColumnIndexOrThrow("power"))
            heroChar = HeroChar(id, name, company, origin, power)
        }
        cursor.close()
        db.close()
        return heroChar
    }

    fun updateHeroChar(heroChar: HeroChar): Int {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("name", heroChar.name)
            put("company", heroChar.company)
            put("origin", heroChar.origin)
            put("power", heroChar.power)
        }
        val rowsAffected = db.update(DBHelper.TABLE_NAME, values, "id = ?", arrayOf(heroChar.id.toString()))
        db.close()
        return rowsAffected
    }

    fun deleteHeroChar(id: Int): Int {
        val db = dbHelper.writableDatabase
        val rowsAffected = db.delete(DBHelper.TABLE_NAME, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsAffected
    }

    fun getHeroByName(field: String): List<String> {
        val db = dbHelper.readableDatabase
        val cursor: Cursor = db.query(DBHelper.TABLE_NAME, arrayOf("name"), "name LIKE ?", arrayOf("%$field%"), null, null, null)

        val charList = mutableListOf<String>()
        while(cursor.moveToNext()) {
            //val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            //val company = cursor.getString(cursor.getColumnIndexOrThrow("company"))
            //val origin = cursor.getString(cursor.getColumnIndexOrThrow("origin"))
            //val power = cursor.getString(cursor.getColumnIndexOrThrow("power"))
            charList.add(name)
        }
        cursor.close()
        db.close()
        return charList
    }
}