package asiantech.internship.summer.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class CanvasActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        val checkFragment = CheckFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.frameLayoutCanvas, checkFragment)
                .commit()
    }
}