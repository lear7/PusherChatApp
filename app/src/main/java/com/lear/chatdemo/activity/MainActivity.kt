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

        // DI test
        // truck.deliver()
        // viewModel.doWork()

        // this.getSN()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        // binding.username.setText("user_${this.getSN()}")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // binding.username.setText("user_${this.getSN()}")
    }

    private fun initViews() {
        binding.btnLogin.clicks().onEach {
            App.count = binding.channelCount.text.toString().toInt()
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            this@MainActivity.finish()
        }.launchIn(lifecycleScope)

        binding.radioUserName.checkedChanges().onEach {
            when (it) {
                R.id.user_name1 -> {
                    App.fromUser = binding.userName1.text.toString()
                    App.toUser = binding.userName2.text.toString()
                }
                R.id.user_name2 -> {
                    App.fromUser = binding.userName2.text.toString()
                    App.toUser = binding.userName1.text.toString()
                }
                else -> ""
            }
        }.launchIn(lifecycleScope)

        binding.radioArea.checkedChanges().skipInitialValue().onEach {
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