package com.lear.chatdemo.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.lear.chatdemo.App
import com.lear.chatdemo.R
import com.lear.chatdemo.databinding.ActivityMainBinding
import com.lear.chatdemo.di.MyViewModel
import com.lear.chatdemo.di.Truck
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import reactivecircus.flowbinding.android.view.clicks
import reactivecircus.flowbinding.android.widget.checkedChanges
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
        binding.btnLogin.clicks().onEach {
            if (binding.username.text.isNotEmpty()) {
                val user = binding.username.text.toString()
                App.user = user
                App.count = binding.channelCount.text.toString().toInt()
                startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                this@MainActivity.finish()
            } else {
                Toast.makeText(
                    applicationContext,
                    "请输入用户名",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }.launchIn(lifecycleScope)

        binding.radioArea.checkedChanges().skipInitialValue().onEach {
            delay(3000)
            when (it) {
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
            Toast.makeText(
                applicationContext,
                "切换成功",
                Toast.LENGTH_SHORT
            ).show()
        }.launchIn(lifecycleScope)
    }
}