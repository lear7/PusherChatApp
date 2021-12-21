package com.lear.chatdemo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.lear.chatdemo.App
import com.lear.chatdemo.R
import com.lear.chatdemo.databinding.ActivityMainBinding
import com.lear.chatdemo.di.MyViewModel
import com.lear.chatdemo.di.Truck
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var truck: Truck

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        truck.deliver()
        viewModel.doWork()

        initViews()
    }

    private fun initViews() {

//        findViewById<Button>(R.id.btnLogin)
//            .clicks() // binding API available in flowbinding-android
//            .onEach {
//                // handle button clicked
//            }
//            .launchIn(uiScope)

//        uiScope.launch {
//            findViewById<Button>(R.id.button)
//                .clicks() // this returns a Flow<Unit>
//                .collect {
//                    // handle button clicked
//                }
//        }

        binding.btnLogin.setOnClickListener {
            if (binding.username.text.isNotEmpty()) {
                val user = binding.username.text.toString()
                App.user = user
                App.count = binding.channelCount.text.toString().toInt()
                startActivity(Intent(this@MainActivity, ChatActivity::class.java))
            } else {
                Toast.makeText(
                    applicationContext,
                    "请输入用户名",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.radioArea.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_area_1 -> {
                    App.cluster = "ap1"
                }
                R.id.radio_area_2 -> {
                    App.cluster = "us2"
                }
                R.id.radio_area_3 -> {
                    App.cluster = "eu"
                }
            }
        }
    }
}