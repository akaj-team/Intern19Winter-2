package asiantech.internship.summer.recyclerView

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-cuongle`.fragment_time_line.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class TimeLineFragment : Fragment() {

    companion object {
        private const val DELAYS_PROGRESSBAR: Long = 2000
        private const val ARG_URI_IMAGE = "image"
        fun newInstance(uri: String?) = TimeLineFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_URI_IMAGE, uri)
            }
        }
    }

    private var uriFood: String? = null
    private var service: TimeLineService? = null
    private var callAPI: TimeLineService? = null
    private lateinit var adapterTimeLine: TimeLines
    private val timelineItems = mutableListOf<TimeLineItem>()
    private var isLoading = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            uriFood = it.getString(ARG_URI_IMAGE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_time_line, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        service = TimeLineAPI.newInstance()?.create(TimeLineService::class.java)
        callAPI = service
        initListeners()
        initData()
        uriFood?.let {
            Log.i("XXX", it)
            addTimeLine(it)
        }
    }

    private fun initData() {
        callAPI?.getAll()?.enqueue(object : retrofit2.Callback<List<TimeLineItem>> {
            override fun onFailure(call: Call<List<TimeLineItem>>, t: Throwable) {

            }

            override fun onResponse(call: Call<List<TimeLineItem>>, response: Response<List<TimeLineItem>>) {
                if (response.body() == null) {
                    return
                }
                timelineItems.addAll(response.body()!!)
                initAdapter()
            }
        })
    }

    private fun initAdapter() {
        adapterTimeLine = TimeLines(timelineItems)

        adapterTimeLine.onItemClicked = { it ->
            timelineItems[it].let {
                if (it.isLike) {
                    it.isLike = false
                    Log.i("XXX", it.id.toString())
                    it.like -= 1
                    updateTimeLine(it.id, it)
                } else {
                    it.isLike = true
                    it.like += 1
                    updateTimeLine(it.id, it)
                }
            }
            adapterTimeLine.notifyItemChanged(it, null)
            (recycleView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        }

        adapterTimeLine.onItemDeleteClick = {
            removeTimeLine(timelineItems[it].id)
            timelineItems.removeAt(it)
            adapterTimeLine.notifyItemChanged(it, null)
        }
        recycleView.adapter = adapterTimeLine
    }

    private fun initListeners() {

        itemRefresh.setOnRefreshListener {
            timelineItems.clear()
            initData()
            itemRefresh.isRefreshing = false
        }

        btnAdd.setOnClickListener {
            (activity as? TimelineActivity)?.replaceFragment(AddTimeLineFragment())
//            addTimeLine("content://media/external/images/media/257677")
        }

        recycleView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == timelineItems.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            loadMore()
                        }, DELAYS_PROGRESSBAR)
                    }
                }
                isLoading = false
            }
        })
    }

    private fun loadMore() {
        isLoading = true
        progressBar.visibility = View.INVISIBLE
        initData()
    }

    private fun updateTimeLine(id: Int, timeLineItem: TimeLineItem) {
        callAPI?.updateTimeLine(id, timeLineItem)?.enqueue(object : retrofit2.Callback<TimeLineItem> {
            override fun onFailure(call: Call<TimeLineItem>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TimeLineItem>, response: Response<TimeLineItem>) {
                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun removeTimeLine(id: Int) {
        callAPI?.deleteTimeLine(id)?.enqueue(object : retrofit2.Callback<TimeLineItem> {
            override fun onFailure(call: Call<TimeLineItem>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<TimeLineItem>, response: Response<TimeLineItem>) {
                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
            }

        })
    }

    private fun addTimeLine(path: String) {
        val filePath = File(path)
        val requestFile = RequestBody.create(MediaType.parse("image/*"), filePath)
        val body = MultipartBody.Part.createFormData("imageFood", filePath.name, requestFile)
        val userName = RequestBody.create(MediaType.parse("text/plain"), filePath.name)
        val call = callAPI?.addTimeLine(userName, body)

        call?.enqueue(object : Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                Toast.makeText(requireContext(), response.message(), Toast.LENGTH_SHORT).show()
            }

        })

    }
}
