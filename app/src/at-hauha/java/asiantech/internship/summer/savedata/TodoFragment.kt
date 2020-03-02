package asiantech.internship.summer.savedata

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import asiantech.internship.summer.R
import com.daimajia.swipe.SwipeLayout
import kotlinx.android.synthetic.`at-hauha`.fragment_todo.*

class TodoFragment : Fragment() {

    private lateinit var adapter: TodoAdapter
    private lateinit var sample: SwipeLayout

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
    }

    private fun initAdapter(view: View) {
        adapter = TodoAdapter()
        adapter.onItemClick = {
            sample = view.findViewById(R.id.swipeLayout)
            sample.showMode = SwipeLayout.ShowMode.LayDown
            sample.addDrag(SwipeLayout.DragEdge.Right, sample.findViewWithTag("llItem"))
            sample.findViewById<ImageView>(R.id.imgDelete).setOnClickListener { Toast.makeText(requireContext(), "Delete", Toast.LENGTH_SHORT).show() }
        }
        recycle_view.layoutManager = LinearLayoutManager(requireContext())
        recycle_view.adapter = adapter
    }

}
