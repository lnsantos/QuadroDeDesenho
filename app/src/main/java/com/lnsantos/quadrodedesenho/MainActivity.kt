package com.lnsantos.quadrodedesenho

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.lnsantos.features.drawer.PaintingDrawView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(PaintingDrawView(this))
    }

}