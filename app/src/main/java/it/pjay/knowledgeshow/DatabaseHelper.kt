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
        fillDatabase(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
    fun insertData(db: SQLiteDatabase?, category: String, question: String, answer: String, fact: String ) {
//        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_CATEGORY, category)
            put(COLUMN_QUESTION, question)
            put(COLUMN_ANSWER, answer)
            put(COLUMN_FACT, "randomfact")
        }
        if (db != null) {
            db.insert(TABLE_NAME, null, values)
        }
        
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

    fun fillDatabase(db: SQLiteDatabase?){
        insertData(db,"Historia", "Który władca założył stolicę Cesarstwa Niemieckiego w Berlinie?", "Wilhelm I", "ciekawostka 1")
        insertData(db,"Historia", "Który kraj był pierwszym, który uznał niepodległość Stanów Zjednoczonych?", "Maroko", "ciekawostka 2")
        insertData(db,"Historia", "Który z tych papieży nie był Włochem?", "Jan Paweł II", "ciekawostka 3")
        insertData(db,"Geografia", "Które państwo ma najwięcej jezior?", "Kanada", "ciekawostka 4")
        insertData(db,"Geografia", "Który kontynent jest największy pod względem liczby ludności?", "Azja", "ciekawostka 5")
        insertData(db,"Geografia", "Który z krajów nie ma dostępu do Morza Śródziemnego?", "Macedonia Północna", "ciekawostka 6")
        insertData(db,"Biologia", "Jak nazywa się gruczoł odpowiedzialny za wydzielanie insuliny?", "Trzustka", "ciekawostka 7")
        insertData(db,"Biologia", "Który pierwiastek jest składnikiem hemoglobiny?", "Żelazo", "ciekawostka 8")
        insertData(db, "Biologia", "Jak nazywa się największy układ nerwowy u człowieka?", "Ośrodkowy Układ Nerwowy", "ciekawostka 9")
        insertData(db, "Matematyka", "Jaki jest wynik mnożenia 111 przez 111?", "12321", "ciekawostka 10")
        insertData(db, "Matematyka", "Ile wynosi pierwiastek kwadratowy z 121?", "11", "ciekawostka 11")
        insertData(db, "Matematyka", "Jak nazywa się linia przecinająca oś symetrii paraboli?", "Oś równoległa", "ciekawostka 12")
        insertData(db, "Sport", "Która drużyna wygrała najwięcej Super Bowlów w historii NFL?", "New England Patriots", "ciekawostka 13")
        insertData(db, "Sport", "Który tenisista jest rekordzistą pod względem liczby zwycięstw w turniejach Wielkiego Szlema?", "Roger Federer", "ciekawostka 14")
        insertData(db, "Sport", "Który klub piłkarski jest rekordzistą pod względem liczby zdobytych Lig Mistrzowskich?", "Real Madryt", "ciekawostka 15")
        insertData(db, "Film", "Kto zagrał główną rolę w filmie \"Rocky\"?", "Sylvester Stallone", "ciekawostka 16")
        insertData(db, "filmy", "Jak nazywał się statek, na którym przemieszczał się bohater filmu Titanic?", "Titanic", "ciekawostka 4")
        insertData(db, "filmy", "Kto zagrał postać Forresta Gumpa w filmie o tym samym tytule?", "Tom Hanks", "ciekawostka 5")
        insertData(db, "filmy", "W jakim roku miał swoją premierę film Pulp Fiction?", "1994", "ciekawostka 6")
        insertData(db, "sport", "Z jakiego kraju pochodzi tenisistka Naomi Osaka?", "Japonia", "ciekawostka 7")
        insertData(db, "sport", "Ile złotych medali olimpijskich zdobył Usain Bolt?", "8", "ciekawostka 8")
        insertData(db, "sport", "Jakie drużyny rywalizują w meczu El Clásico?", "FC Barcelona i Real Madryt", "ciekawostka 9")
        insertData(db, "historia", "Który rok uważany jest za początek II wojny światowej?", "1939", "ciekawostka 10")
        insertData(db, "historia", "Który z wodzów nazywany był 'Wielkim'? (podpowiedź: Indianie)", "Wielki Wódz", "ciekawostka 11")
        insertData(db, "historia", "W którym roku podpisano traktat wersalski kończący I wojnę światową?", "1919", "ciekawostka 12")
        insertData(db, "nauka", "Jaka jest najmniejsza cząstka pierwiastka?", "Atom", "ciekawostka 13")
        insertData(db, "nauka", "Co oznacza skrót DNA?", "Kwas deoksyrybonukleinowy", "ciekawostka 14")
        insertData(db, "nauka", "Ile wynosi liczba pi?", "3,14", "ciekawostka 15")
        insertData(db, "muzyka", "Jak nazywał się ostatni studyjny album zespołu The Beatles?", "Let It Be", "ciekawostka 16")
        insertData(db, "muzyka", "Która z piosenek Madonny zdobyła najwięcej nagród Grammy?", "Ray of Light", "ciekawostka 17")
        insertData(db, "muzyka", "Który z zespołów wydał album 'The Dark Side of the Moon'?", "Pink Floyd", "ciekawostka 18")
        insertData(db, "geografia", "Jaka jest stolica Australii?", "Canberra", "ciekawostka 19")
        insertData(db, "geografia", "Które miasto jest stolicą Rosji?", "Moskwa", "ciekawostka 20")
        insertData(db, "geografia", "Które państwo ma najwięcej ludności na świecie?", "Chiny", "ciekawostka 21")
    }
}