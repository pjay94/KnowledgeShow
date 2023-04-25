package it.pjay.knowledgeshow

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "knowledgeshow"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "questions"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_QUESTION = "question"
        private const val COLUMN_ANSWER = "answer"
        private const val COLUMN_FACT = "fact"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable =
            "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_CATEGORY TEXT, $COLUMN_QUESTION TEXT, $COLUMN_ANSWER TEXT, $COLUMN_FACT TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(category: String, question: String, answer: String, fact: String ): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_QUESTION, question)
            put(COLUMN_ANSWER, answer)
            put(COLUMN_FACT, "randomfact")
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun getRandomEntry(): QuestionEntry? {
        val db = this.readableDatabase
        val cursor: Cursor =
            db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY RANDOM() LIMIT 1", null)
        var questionEntry: QuestionEntry? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val category = cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY))
            val question = cursor.getString(cursor.getColumnIndex(COLUMN_QUESTION))
            val answer = cursor.getString(cursor.getColumnIndex(COLUMN_ANSWER))
            val fact = cursor.getString(cursor.getColumnIndex(COLUMN_FACT))
            questionEntry = QuestionEntry(id, category, question, answer, fact)
        }
        cursor.close()
        return questionEntry
    }
}