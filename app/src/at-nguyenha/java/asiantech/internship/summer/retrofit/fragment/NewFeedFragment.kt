package asiantech.internship.summer.retrofit.fragment

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import asiantech.internship.summer.retrofit.RecyclerViewMainActivity
import asiantech.internship.summer.retrofit.adapter.NewFeedAdapter
import asiantech.internship.summer.retrofit.api.ClientAPI
import asiantech.internship.summer.retrofit.api.NewFeedAPI
import asiantech.internship.summer.retrofit.model.NewFeedModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_new_feed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewFeedFragment : Fragment() {

    private var newfeeds = mutableListOf<NewFeedModel>()
    private var adapterNewFeeds = NewFeedAdapter(newfeeds)
    private var isLoading = false
    private val delayTime: Long = 2000

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_feed, container, false)
        val toolbar = view?.findViewById<Toolbar>(R.id.tbNewFeed)
        (requireActivity() as AppCompatActivity).setSupportActionBar(toolbar)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuDelete){
            (activity as RecyclerViewMainActivity).replaceFragment(AddNewFeedFragment(), true)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getListAPI()
        initListener()
    }

    private fun initAdapter() {
        recyclerViewMain.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMain.adapter = adapterNewFeeds
        adapterNewFeeds.onItemClicked = {
            val isHeart = newfeeds[it].isHeart
            if (isHeart) {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber--
            } else {
                newfeeds[it].isHeart = !isHeart
                newfeeds[it].likeNumber++
            }
            initHeart(newfeeds[it].id, newfeeds[it])
            adapterNewFeeds.notifyItemChanged(it, null)
            (recyclerViewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        adapterNewFeeds.onItem3DotClicked = {
            deleteNewFeed(newfeeds[it].id)
        }
    }

    private fun initListener() {
        srlRefreshItem.setOnRefreshListener {
            Handler().postDelayed({
                newfeeds.clear()
                getListAPI()
                adapterNewFeeds.notifyDataSetChanged()
                srlRefreshItem.isRefreshing = false
            }, delayTime)
        }

        recyclerViewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastVisibleItem == newfeeds.size - 1) {
                        progressBar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            adapterNewFeeds.notifyDataSetChanged()
                            isLoading = false
                            progressBar.visibility = View.INVISIBLE
                        }, delayTime)
                    }
                }
            }
        })
    }

    private fun getListAPI() {
        val service = ClientAPI.createServiceClient()?.create(NewFeedAPI::class.java)
        val call = service?.getAllNewFeed()
        call?.enqueue(object : Callback<MutableList<NewFeedModel>> {
            override fun onFailure(call: Call<MutableList<NewFeedModel>>, t: Throwable) {
            }

            override fun onResponse(call: Call<MutableList<NewFeedModel>>, response: Response<MutableList<NewFeedModel>>) {
                response.body().let {
                    it?.let { it1 -> newfeeds.addAll(it1) }
                }
                initAdapter()
            }
        })
    }

    private fun deleteNewFeed(id: Int) {
        val service = ClientAPI.createServiceClient()?.create(NewFeedAPI::class.java)
        val call = service?.deleteNewFeed(id)
        call?.enqueue(object : Callback<NewFeedModel> {
            override fun onFailure(call: Call<NewFeedModel>, t: Throwable) {
            }

            override fun onResponse(call: Call<NewFeedModel>, response: Response<NewFeedModel>) {
            }
        })
    }

    private fun initHeart(id: Int, newFeed: NewFeedModel) {
        val service = ClientAPI.createServiceClient()?.create(NewFeedAPI::class.java)
        val call = service?.updateNewFeed(id, newFeed)
        call?.enqueue(object : Callback<NewFeedModel> {
            override fun onFailure(call: Call<NewFeedModel>, t: Throwable) {
            }
            override fun onResponse(call: Call<NewFeedModel>, response: Response<NewFeedModel>) {
            }
        })
    }
}
