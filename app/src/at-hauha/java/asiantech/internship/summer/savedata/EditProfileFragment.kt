package asiantech.internship.summer.savedata

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.savedata.model.AppDatabase
import asiantech.internship.summer.savedata.model.User
import kotlinx.android.synthetic.`at-hauha`.fragment_edit_profile.*

@Suppress("NAME_SHADOWING")
class EditProfileFragment : Fragment() {

    companion object {
        private const val PERMISSION_CODE = 100
        private const val ARG_USER = "user"
        fun newInstance(user: User) = EditProfileFragment().apply {
            arguments = Bundle().apply {
                putSerializable(ARG_USER, user)
            }
        }
    }

    private var imageUri = ""
    private var db: AppDatabase? = null
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.apply {
            user = getSerializable(ARG_USER) as User?
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        edtEmail.text = Editable.Factory.getInstance().newEditable(user?.username)
        edtPassword.text = Editable.Factory.getInstance().newEditable(user?.password)
        if (user?.path?.run { isEmpty() }!!) {
            imgAvatar.setImageResource(R.drawable.ic_avatar)
        } else {
            imgAvatar.setImageURI(Uri.parse(user?.path))
        }
        imgAvatar.setOnClickListener {
            checkPermission()
        }
        db = AppDatabase.connectDatabase(requireContext())
        imgSave.setOnClickListener {
            val name = edtEmail.text.toString().trim()
            val password = edtPassword.text.toString().trim()
            var path = user?.path
            if (name.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), getString(R.string.toat_wrong), Toast.LENGTH_SHORT).show()
            } else {
                if (imageUri.isNotBlank()) {
                    path = imageUri
                }
                user?.id?.let { it1 -> path?.let { it2 -> db?.userDao()?.updateData(name, password, it2, it1) } }
            }
            user = db?.userDao()?.findUser(name, password)
            user?.id?.let { it1 -> (activity as? TodoActivity)?.replaceMenuFragment(it1) }
        }
        imgExit.setOnClickListener {
            user?.id?.let { it1 -> (activity as? TodoActivity)?.replaceMenuFragment(it1) }
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                val permission = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                requestPermissions(permission, PERMISSION_CODE)
            } else {
                user?.let { (activity as? TodoActivity)?.replacePictureFragment(it) }
            }
        } else {
            user?.let { (activity as? TodoActivity)?.replacePictureFragment(it) }
        }
    }

}