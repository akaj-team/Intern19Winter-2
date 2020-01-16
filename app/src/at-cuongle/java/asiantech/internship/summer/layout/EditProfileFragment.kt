package asiantech.internship.summer.layout


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_edit_profile.*

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSave.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer, LoginFragment.getInstance(), null)
                    ?.addToBackStack(null)
                    ?.commit()
        }
        tvEditPicture.setOnClickListener {
            Log.i("XXX","1")
        }
    }


}
