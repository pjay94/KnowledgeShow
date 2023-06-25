package it.pjay.knowledgeshow

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameActivity : AppCompatActivity() {
    var context: Context = this
    var mpCorrect: MediaPlayer? = null
    var mpWrong: MediaPlayer? = null
    val dbHelper: DatabaseHelper = DatabaseHelper(context)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        supportActionBar?.hide()

        val buttonShowAnswer = findViewById<Button>(R.id.show_answer)
        val buttonNextQuestion = findViewById<Button>(R.id.next_question)
        val greenButton = findViewById<Button>(R.id.green_button)
        val redButton = findViewById<Button>(R.id.red_button)
        val answerText = findViewById<TextView>(R.id.answer)
        val categoryText = findViewById<TextView>(R.id.category)
        val questionText = findViewById<TextView>(R.id.question)

        var questionEntry: QuestionEntry? = dbHelper.getRandomEntry()
        if (questionEntry != null) {
            questionText.setText(questionEntry.question)
            categoryText.setText(questionEntry.category)
            answerText.setText(questionEntry.answer)
        }

        mpCorrect = MediaPlayer.create(context, R.raw.correctanswer);
        mpWrong = MediaPlayer.create(context, R.raw.wronganswer);

        buttonShowAnswer.setOnClickListener {
            buttonNextQuestion.visibility = View.VISIBLE
            buttonShowAnswer.visibility = View.INVISIBLE
            greenButton.visibility = View.VISIBLE
            redButton.visibility = View.VISIBLE
            answerText.visibility = View.VISIBLE
        }

        buttonNextQuestion.setOnClickListener {
            buttonNextQuestion.visibility = View.INVISIBLE
            buttonShowAnswer.visibility = View.VISIBLE
            greenButton.visibility = View.INVISIBLE
            redButton.visibility = View.INVISIBLE
            answerText.visibility = View.INVISIBLE
//            categoryText.text = (Math.random() * 100).toString()
//            questionText.text = (Math.random() * 100).toString()
            questionEntry= dbHelper.getRandomEntry()

            if (questionEntry != null) {
                questionText.setText(questionEntry!!.question)
                categoryText.setText(questionEntry!!.category)
                answerText.setText(questionEntry!!.answer)
            }
        }

        greenButton.setOnClickListener(View.OnClickListener {
            try {
                if (mpCorrect!!.isPlaying()) {
                    mpCorrect!!.stop()
                    mpCorrect!!.release()
                    mpCorrect = MediaPlayer.create(context, R.raw.correctanswer)
                }
                mpCorrect!!.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        redButton.setOnClickListener(View.OnClickListener {
            try {
                if (mpWrong!!.isPlaying()) {
                    mpWrong!!.stop()
                    mpWrong!!.release()
                    mpWrong = MediaPlayer.create(context, R.raw.wronganswer)
                }
                mpWrong!!.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })
    }
}