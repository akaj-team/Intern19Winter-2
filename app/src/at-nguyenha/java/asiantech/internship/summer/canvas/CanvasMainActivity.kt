package asiantech.internship.summer.canvas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

class CanvasMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas_main)
        replaceFragment(GraphFragment())
    }

    private fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flMainActivity, fragment, null)
        if (isAddToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }
}
