package asiantech.internship.summer.savedata.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.Utils

class LaucherActivity : AppCompatActivity() {

    private var idLogined: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laucher)
        loadLogin()

    }

    private fun loadLogin() {
        val handler = Handler()
        handler.postDelayed({
            val sharedPreferences = this
                    .getSharedPreferences(Utils.SHARED_PREFS, Context.MODE_PRIVATE)
            idLogined = sharedPreferences.getInt(Utils.PUT_ID, Utils.ID_DEFAULT)
            if (idLogined != Utils.ID_DEFAULT) {
                val intent = Intent(this, ToDoActivity::class.java)
                val bundle = Bundle()
                bundle.putInt(Utils.PUT_ID, idLogined!!)
                intent.putExtras(bundle)
                startActivity(intent)
            } else {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }, Utils.DELAY_TIME)
    }
}
