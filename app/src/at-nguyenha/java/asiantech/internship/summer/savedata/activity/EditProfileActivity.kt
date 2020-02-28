package asiantech.internship.summer.savedata.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_register_savedata.*

class EditProfileActivity : AppCompatActivity() {

    private var getAction: String? = ""

    companion object {
        private const val ACTION_EDIT = "edit"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_register_savedata)

        getAction = intent.getStringExtra(ACTION_EDIT)
        if (getAction == ACTION_EDIT) {
            btnRegisterSaveData.text = getString(R.string.button_text_edit_profile)
        }
    }
}
