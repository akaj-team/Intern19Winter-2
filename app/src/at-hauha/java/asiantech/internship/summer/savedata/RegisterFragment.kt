package asiantech.internship.summer.savedata

import android.Manifest
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.Html
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_signup.*

class RegisterFragment : Fragment() {

    companion object {
        private const val PERMISSION_CODE = 100
        private const val ARG_PICTURE = "picture"
        fun newInstance(uri: String) = RegisterFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PICTURE, uri)
            }
        }
    }

    private var imageUri = ""
    private var user: User? = null
    private var db: AppDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            imageUri = getString(ARG_PICTURE).toString()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = "<font color=#329AE7>Already</font><font color=#ffffff> have an account? Sign-in</font>"
        tvSignUp.text = Html.fromHtml(text, 1)
        setAvatar()
        db = AppDatabase.connectDatabase(requireContext())
        tvRegister.setOnClickListener {
            val name = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            var path = ""
            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), R.string.toat_wrong, Toast.LENGTH_SHORT).show()
            } else {
                if (imageUri.isNotBlank()) {
                    path = imageUri
                }
                if(db?.userDao()?.getUserName(name) == null){
                    user = User(username = name, password = password, path = path)
                    user?.let { it1 -> db?.userDao()?.insertAll(it1) }
                }
                else{
                    Toast.makeText(requireContext(),getString(R.string.valid_username),Toast.LENGTH_LONG).show()
                }
                edtEmail.text = Editable.Factory.getInstance().newEditable("")
                edtPassword.text = Editable.Factory.getInstance().newEditable("")
            }
            d("TAG11", "----" + db?.userDao()?.findUser(name, password))
        }
        imgAvatar.setOnClickListener {
            checkPermission()
        }
        tvSignUp.setOnClickListener {
            (activity as TodoActivity).replaceLoginFragment()
        }
    }


    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                (activity as? TodoActivity)?.replaceEditPictureFragment()
            }
        } else {
            (activity as? TodoActivity)?.replaceEditPictureFragment()
        }
    }


    private fun setAvatar() {
        if (imageUri.isNotBlank()) {
            imgAvatar.setImageURI(Uri.parse(imageUri))
        }
    }

}
