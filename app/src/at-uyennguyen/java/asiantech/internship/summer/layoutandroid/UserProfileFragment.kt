package asiantech.internship.summer.layoutandroid

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_user_profile.*

class UserProfileFragment : Fragment(), ChangeAvatar {
    override fun image(img: Bitmap) {
        avatar.setImageBitmap(img)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        penciledit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val editProfileFragment: EditProfileFragment = EditProfileFragment()
                editProfileFragment.changeAvatar = this@UserProfileFragment
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, editProfileFragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }
}

