package asiantech.internship.summer.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R

class CanvasMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas_main)
        supportFragmentManager.beginTransaction()
                .replace(R.id.clContainer, ChessBoardFragment(), null)
                .addToBackStack(null)
                .commit()
    }
}
