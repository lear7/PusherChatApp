package com.gkd.projectx.welcome

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.gkd.projectx.R
import com.gkd.projectx.databinding.ActivitySplashBinding
import com.gkd.projectx.welcome.login.LoginFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenResumed {
            delay(10)
            supportFragmentManager.beginTransaction().replace(R.id.container, LoginFragment())
                .commitAllowingStateLoss()
        }

    }

}