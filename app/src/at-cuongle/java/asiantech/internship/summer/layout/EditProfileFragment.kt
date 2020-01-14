package asiantech.internship.summer.layout


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R

/**
 * A simple [Fragment] subclass.
 */
class EditProfileFragment : Fragment() {
    companion object {
        fun getInstance() = EditProfileFragment()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_profile, container, false)
    }


}
