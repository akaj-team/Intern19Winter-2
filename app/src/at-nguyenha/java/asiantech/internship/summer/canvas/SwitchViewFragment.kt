package asiantech.internship.summer.canvas


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-nguyenha`.fragment_switch_view.*

class SwitchViewFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_switch_view, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchView()
    }

    private fun switchView(){
        btnChessView.setOnClickListener {
            (activity as CanvasMainActivity).replaceFragment(ChessBoardFragment(), true)
        }
        btnGraphView.setOnClickListener {
            (activity as CanvasMainActivity).replaceFragment(GraphFragment(), true)
        }
    }
}
