package asiantech.internship.summer.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

class CanvasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)
        replayFragment(ChineseChessFragment())
    }
    fun replayFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .replace(R.id.flContainer,fragment,null)
                .addToBackStack(null)
                .commit()
    }
}