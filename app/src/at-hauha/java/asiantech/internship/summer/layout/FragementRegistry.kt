package asiantech.internship.summer.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-hauha`.fragment_registry.*

class FragementRegistry:Fragment() {
    companion object{
        fun getInstance() = FragementRegistry()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_registry,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtLogin.setOnClickListener {
            fragmentManager?.beginTransaction()
                    ?.replace(R.id.flContainer,FragmentLogin.getInstance(),null)
                    ?.addToBackStack(null)
                    ?.commit()
        }

    }
}