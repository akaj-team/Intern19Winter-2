package asiantech.internship.summer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.`at-daiho`.fragment_edit_profile.*

class EditProfileFragment : Fragment() {

    companion object {
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
    }

}
