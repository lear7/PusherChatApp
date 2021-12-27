package com.gkd.demo_app_mvi.layer_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.gkd.demo_app_mvi.R
import com.gkd.demo_app_mvi.layer_app.activity.HomeActivity
import com.gkd.demo_app_mvi.layer_app.fragment.HomeFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        findViewById<Button>(R.id.btn_activity).setOnClickListener {
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        findViewById<Button>(R.id.btn_fragment).setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.layout_container, HomeFragment())
                .commitAllowingStateLoss()

            findViewById<LinearLayout>(R.id.btn_container).isVisible = false
            findViewById<FrameLayout>(R.id.layout_container).isVisible = true
        }
    }

    override fun onResume() {
        super.onResume()

        findViewById<LinearLayout>(R.id.btn_container).isVisible = true
        findViewById<FrameLayout>(R.id.layout_container).isVisible = false
    }
}