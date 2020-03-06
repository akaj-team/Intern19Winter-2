package asiantech.internship.summer.retrofit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.retrofit.fragment.NewFeedFragment

class RecyclerViewMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        replaceFragment(NewFeedFragment())
    }

    internal fun replaceFragment(fragment: Fragment, isAddToBackStack: Boolean = false) {
        val ft = supportFragmentManager.beginTransaction()
        ft.replace(R.id.flMainActivity, fragment, null)
        if (isAddToBackStack) {
            ft.addToBackStack(null)
        }
        ft.commit()
    }
}
