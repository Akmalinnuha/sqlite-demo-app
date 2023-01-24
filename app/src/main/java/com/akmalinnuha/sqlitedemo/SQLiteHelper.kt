package com.akmalinnuha.sqlitedemo

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "student.db"
        private const val TABLE_STUDENT = "student_tbl"

        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTABLE = ("CREATE TABLE "+ TABLE_STUDENT + "("
                + KEY_ID + " INTEGER PRIMARY_KEY," + KEY_NAME + " TEXT,"
                + KEY_EMAIL + " TEXT" + ")")
        db?.execSQL(createTABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        onCreate(db)
    }

    fun insertStudent(std : StudentModel):Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ID,std.id)
        contentValues.put(KEY_NAME,std.name)
        contentValues.put(KEY_EMAIL,std.email)

        val success = db.insert(TABLE_STUDENT,null,contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun getAllStudent() : ArrayList<StudentModel> {
        val mhsList: ArrayList<StudentModel> = ArrayList()

        val selectQuery = "SELECT * FROM $TABLE_STUDENT"

        val db = this.readableDatabase
        var cursor: Cursor? = null

        try {
            cursor = db.rawQuery(selectQuery, null)
        } catch (e: SQLException) {
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                name = cursor.getString(cursor.getColumnIndex(KEY_NAME))
                email = cursor.getString(cursor.getColumnIndex(KEY_EMAIL))

                val student = StudentModel(id = id, name = name, email = email)
                mhsList.add(student)
            } while (cursor.moveToNext())
        }
        return mhsList
    }

    fun updateStudent(std: StudentModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ID,std.id)
        contentValues.put(KEY_NAME,std.name)
        contentValues.put(KEY_EMAIL,std.email)

        val success = db.update(TABLE_STUDENT, contentValues, "id="+std.id, null)
        db.close()
        return success
    }

    fun deleteStudentById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(KEY_ID, id)

        val success = db.delete(TABLE_STUDENT, "id=$id",null)
        db.close()
        return success
    }
}