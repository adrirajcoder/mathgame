package com.adriraj.mathgame

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.adriraj.mathgame.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.buttonAdd.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operator", "+")
            startActivity(intent)
        }

        binding.buttonSub.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operator", "-")
            startActivity(intent)
        }

        binding.buttonMul.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operator", "*")
            startActivity(intent)
        }

        binding.buttonDiv.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("operator", "/")
            startActivity(intent)
        }
    }
}