package asiantech.internship.summer.recyclerview

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_recyclerview.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewFeedFragment : Fragment() {
    companion object {
        const val DELAY_TIME = 2000
    }

    private var listNewFeed = mutableListOf<NewFeed>()
    private lateinit var adapterNewFeed: NewFeedAdapter
    private var isLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initData()
        initListener()
    }

    private fun initAdapter() {
        adapterNewFeed = NewFeedAdapter(listNewFeed)
        recyclerviewMain.adapter = adapterNewFeed
        adapterNewFeed.onItemClickedToLike = {
            //            listNewFeed[it].run {
//                isLikeHeart = isLike
//                if (isLikeHeart) {
//                    isLike = !isLikeHeart
//                    apply {
//                        this.numberLike++
//                    }
//                } else {
//                    isLike = !isLikeHeart
//                    apply {
//                        this.numberLike--
//                    }
//                }
//            }
            updateLike(listNewFeed[it].id, listNewFeed[it], it)
//            adapterNewFeed.notifyItemChanged(it, null)
//            (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        adapterNewFeed.onItemClickedToDelete = {
            deleteNewFeed(listNewFeed[it].id, it)
        }
        adapterNewFeed.onItemClickedToAdd = {
            val addNewFeedFragment = AddNewFeedFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.relativeLayout, addNewFeedFragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    private fun initListener() {
        recyclerviewMain.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastItem = linearLayoutManager.findLastCompletelyVisibleItemPosition()
                if (!isLoading) {
                    if (lastItem == listNewFeed.size - 1) {
                        progressbar.visibility = View.VISIBLE
                        Handler().postDelayed({
                            initData()
                            isLoading = false
                            progressbar.visibility = View.INVISIBLE
                        }, 2000)
                    }
                }
            }
        })
        swipeRefreshLayout.setOnRefreshListener {
            Handler().postDelayed({
                listNewFeed.clear()
                initData()
                adapterNewFeed.notifyDataSetChanged()
                swipeRefreshLayout.isRefreshing = false
            }, DELAY_TIME.toLong())
        }
    }

    private fun initData() {
        val service = Retrofit.getRetrofitInstance()?.create(NewFeedAPI::class.java)
        val callNewFeed = service?.getNewFeeds()
        callNewFeed?.enqueue(object : Callback<MutableList<NewFeed>> {
            override fun onFailure(call: Call<MutableList<NewFeed>>, t: Throwable) {

            }

            override fun onResponse(call: Call<MutableList<NewFeed>>, response: Response<MutableList<NewFeed>>) {
                if (response.code() in 200..209) {
                    response.body().let {
                        it?.let { it1 -> listNewFeed.addAll(it1) }
                    }
                    adapterNewFeed.notifyDataSetChanged()
                } else {
                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("Error: " + response.code())
                    dialog.show()
                }
            }
        })
    }

    private fun updateLike(id: Int, newFeed: NewFeed, position: Int) {
        val service = Retrofit.getRetrofitInstance()?.create(NewFeedAPI::class.java)
        val callNewFeed = service?.updateNewFeed(id, newFeed)
        callNewFeed?.enqueue(object : Callback<NewFeed> {
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {

            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                if (response.code() in 200..209) {
                    var isLikeHeart: Boolean
                    listNewFeed[position].run {
                        isLikeHeart = isLike
                        if (isLikeHeart) {
                            isLike = !isLikeHeart
                            apply {
                                this.numberLike++
                            }
                        } else {
                            isLike = !isLikeHeart
                            apply {
                                this.numberLike--
                            }
                        }
                    }
                    adapterNewFeed.notifyItemChanged(position, null)
                    (recyclerviewMain.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
                } else {
                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("Error: " + response.code())
                    dialog.show()
                }
            }

        })
    }

    private fun deleteNewFeed(id: Int, position: Int) {
        val service = Retrofit.getRetrofitInstance()?.create(NewFeedAPI::class.java)
        val callNewFeed = service?.deleteNewFeed(id)
        callNewFeed?.enqueue(object : Callback<NewFeed> {
            override fun onFailure(call: Call<NewFeed>, t: Throwable) {

            }

            override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {
                if (response.code() in 200..209) {
                    listNewFeed.removeAt(position)
                    adapterNewFeed.notifyItemRemoved(position)
                } else {
                    val dialog = AlertDialog.Builder(context)
                    dialog.setTitle("Error: " + response.code())
                    dialog.show()
                }
            }

        })
    }
}
