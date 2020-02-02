package asiantech.internship.summer.layoutandroid

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_user_profile.*

class UserProfileFragment : Fragment(), ChangeAvatar {
    override fun profile(fullname: String, bio: String) {
        name.setText(fullname)
        address.setText(bio)
        Log.d("text", "ok")
    }

    companion object{
        val NAME:String="name"
        val EMAIL:String="email"
        fun newProfile(name:String,email:String): UserProfileFragment {
            val userProfileFragment=UserProfileFragment()
            val bundle=Bundle()
            bundle.putString(NAME,name)
            bundle.putString(EMAIL,email)
            userProfileFragment.arguments=bundle
            return userProfileFragment
        }
    }
    lateinit var picture:Bitmap


    override fun imageUser(img: Bitmap?) {
//        picture=img
        Log.d("imag", "ok")
        avatar.setImageBitmap(img)
    }
     //var userToEdit: UserToEdit?=null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var emailEdit:String
        emailEdit= arguments?.getString(EMAIL).toString()
        name.setText(arguments?.getString(NAME))
        penciledit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //userToEdit?.imageUser(picture)
                val editProfileFragment: EditProfileFragment = EditProfileFragment.profileEdit(name.text.toString(),emailEdit)
                editProfileFragment.changeAvatar = this@UserProfileFragment
                editProfileFragment.bitMap = (avatar.getDrawable() as BitmapDrawable).getBitmap()
                activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frameLayout, editProfileFragment)
                        ?.addToBackStack(null)
                        ?.commit()
            }
        })
    }
}

