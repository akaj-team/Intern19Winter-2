package asiantech.internship.summer

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.`at-daiho`.fragment_edit_profile.*

class EditProfileFragment : Fragment() {

    companion object {
        const val CAMERA_REQUEST_CODE = 101
        const val WRITE_CODE = 102

        fun getInstance() = EditProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btSaveProfile.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flRootContainer, LoginFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        btEditProfilePicture.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST_CODE)
            }
        })

    }

}
