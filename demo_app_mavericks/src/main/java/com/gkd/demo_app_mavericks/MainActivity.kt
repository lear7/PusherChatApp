package com.gkd.demo_app_mavericks

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.gkd.demo_app_mavericks.counter.CounterFragment
import com.gkd.demo_app_mavericks.hello.HelloWorldFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_hello).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HelloWorldFragment()).commitAllowingStateLoss()
        }

        findViewById<Button>(R.id.btn_counter).setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CounterFragment()).commitAllowingStateLoss()
        }
    }
}