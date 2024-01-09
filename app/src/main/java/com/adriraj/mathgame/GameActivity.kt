package com.adriraj.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.adriraj.mathgame.databinding.ActivityGameActivityBinding
import java.util.Locale
import kotlin.random.Random

class GameActivity : AppCompatActivity() {
    lateinit var binding: ActivityGameActivityBinding
    private var correctAnswer = 0
    private var score = 0
    private var life = 3
    private lateinit var timer: CountDownTimer
    private var startTimerInMillis : Long = 60000
    private var timeLeftInMillis: Long = startTimerInMillis
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityGameActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val operator = intent.getStringExtra("operator")

        if(operator == "+"){
            binding.toolbar.title = "Addition"
            gameAddContinue()
        }else if (operator == "-"){
            binding.toolbar.title = "Subtraction"
            gameSubContinue()
        } else if (operator == "*"){
            binding.toolbar.title = "Multiplication"
            gameMulContinue()
        } else {
            binding.toolbar.title = "Divison"
            gameDivContinue()
        }

        binding.ok.setOnClickListener {

            val input = binding.answer.text.toString()

            if(input.toString() == ""  ){
                Toast.makeText(applicationContext, "Please write an answer or click the next button", Toast.LENGTH_LONG).show()
            } else if(input.toString().toIntOrNull() == null){
                Toast.makeText(applicationContext, "Please write a valid answer or click the next button", Toast.LENGTH_LONG).show()
                binding.answer.setText("")
            }
            else {
                pauseTimer()
                val userAnswer = binding.answer.text.toString()
                if (userAnswer.toInt() == correctAnswer){
                    score += 10
                    binding.question.text = "Congratulations, your answer is correct"
                    binding.score.text = score.toString()
                    binding.answer.setText("")
                } else {
                    life--
                    binding.question.text = "Sorry, your answer was wrong"
                    binding.lifecount.text = life.toString()
                    binding.answer.setText("")
                }
            }
        }

        binding.next.setOnClickListener {
            pauseTimer()
            resetTimer()

            binding.answer.setText("")

            if(life == 0){
                Toast.makeText(this@GameActivity, "Game Over", Toast.LENGTH_LONG).show()
                val intent = Intent(this@GameActivity, ResultActivity::class.java)
                intent.putExtra("score", score)
                startActivity(intent)
                finish()
            } else {
                if(operator == "+"){
                    gameAddContinue()
                }else if (operator == "-"){
                    gameSubContinue()
                } else if (operator == "*") {
                    gameMulContinue()
                } else {
                    gameDivContinue()
                }
            }
        }
    }

    private fun gameAddContinue(){
        val num1 = Random.nextInt(0,100)
        val num2 = Random.nextInt(0,100)

        binding.question.text = "$num1 + $num2"

        correctAnswer = num1 + num2

        startTimer()
    }

    private fun gameSubContinue(){
        val num1 = Random.nextInt(0,100)
        val num2 = Random.nextInt(0,100)

        binding.question.text = "$num1 - $num2"

        correctAnswer = num1 - num2

        startTimer()
    }

    private fun gameMulContinue(){
        val num1 = Random.nextInt(0,50)
        val num2 = Random.nextInt(0,30)

        binding.question.text = "$num1 * $num2"

        correctAnswer = num1 * num2

        startTimer()
    }

    private fun gameDivContinue(){
        val num1 = Random.nextInt(0,50)
        var num2 = 0
        do {
            num2 = Random.nextInt(0,30)
        }while (num2 == 0)


        binding.question.text = "$num1 / $num2 \n (Closest Whole Number)"

        correctAnswer = num1 / num2

        startTimer()
    }

    private fun startTimer(){
        timer = object : CountDownTimer(timeLeftInMillis, 1000){
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                updateText()
            }

            override fun onFinish() {
                pauseTimer()
                resetTimer()
                updateText()

                life--
                binding.lifecount.text = life.toString()
                binding.question.text = "Sorry, your time is up."
            }

        }.start()
    }

    private fun resetTimer() {
        timeLeftInMillis = startTimerInMillis
        updateText()
    }

    private fun pauseTimer() {
        timer.cancel()
    }

    private fun updateText() {
        val remainingTime : Int = (timeLeftInMillis/1000).toInt()
        binding.time.text = String.format(Locale.getDefault(), "%02d", remainingTime)
    }
}