package asiantech.internship.summer.layout

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.layout.database.DataConnection
import asiantech.internship.summer.layout.database.model.User
import kotlinx.android.synthetic.`at-cuongle`.fragment_sign_up.*

class SignUpFragment : Fragment() {

    companion object {
        private const val ARG_IMAGE = "image"
        private const val PERMISSION_CODE = 100
        fun newInstance(uri: String) = SignUpFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_IMAGE, uri)
            }
        }
    }

    private var uriImage = ""
    private var db: DataConnection? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uriImage = it.getString(ARG_IMAGE).toString()
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        connectDataBase()
        initListeners()
        setImageAvatar()
    }

    private fun initListeners() {
        imgAvatar.setOnClickListener {
            showDialog()
        }
        btnCreateAccount.setOnClickListener {
            user = User(userName = edtEmail.text.toString(), password = edtPassword.text.toString(), path = uriImage)
            if (edtEmail.text.isBlank() || edtPassword.text.isBlank() || uriImage.isBlank()) {
                Toast.makeText(context, getString(R.string.toast_please_insert_data), Toast.LENGTH_SHORT).show()
            } else {
                user?.let { itl -> db?.userDao()?.insertAll(itl) }
                (activity as? TodoMainActivity)?.replaceFragment(UserLoginFragment())
            }

        }
    }

    private fun connectDataBase() {
        db = DataConnection.connectData(requireContext())
    }

    private fun setImageAvatar() {
        if (uriImage.isNotBlank()) {
            imgAvatar.setImageURI(uriImage.toUri())
        }
    }

    private fun showDialog() {
        val dialogOption = this.let { AlertDialog.Builder(requireContext()) }
        dialogOption.apply {
            setTitle(getString(R.string.title_action_change_avatar))
            setPositiveButton(android.R.string.ok) { _, _ ->
                requestPermission()
                Toast.makeText(context, getString(R.string.toast_creat_now), Toast.LENGTH_SHORT).show()
            }
            setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(context, getString(R.string.toast_cancel), Toast.LENGTH_SHORT).show()
            }
            show()
        }
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context?.let {
                        ContextCompat.checkSelfPermission(
                                it,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    }
                    == PackageManager.PERMISSION_DENIED) {
                val permission =
                        arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                (activity as? TodoMainActivity)?.replaceFragment(GalleryFragment())
            }
        } else {
            (activity as? TodoMainActivity)?.replaceFragment(GalleryFragment())
        }
    }
}
