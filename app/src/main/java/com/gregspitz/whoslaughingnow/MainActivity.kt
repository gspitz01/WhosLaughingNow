package com.gregspitz.whoslaughingnow

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.gregspitz.whoslaughingnow.game.GameFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .add(R.id.contentFrame, GameFragment.newInstance(), "MainContent")
                .commit()
    }
}
