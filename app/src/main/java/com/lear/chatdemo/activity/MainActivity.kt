package com.lear.chatdemo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.lear.chatdemo.App
import com.lear.chatdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnLogin.setOnClickListener {
            if (binding.username.text.isNotEmpty()) {
                val user = binding.username.text.toString()
                App.user = user
                startActivity(Intent(this@MainActivity, ChatActivity::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "请输入用户名",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}