package asiantech.internship.summer.retrofit.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import asiantech.internship.summer.R
import asiantech.internship.summer.retrofit.RecyclerViewMainActivity
import asiantech.internship.summer.retrofit.api.ClientAPI
import asiantech.internship.summer.retrofit.api.NewFeedAPI
import asiantech.internship.summer.retrofit.model.NewFeedModel
import kotlinx.android.synthetic.`at-nguyenha`.fragment_add_new_feed.*
import retrofit2.Response

class AddNewFeedFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_new_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addNewFeed()
    }

    private fun addNewFeed() {
        btnAddNewFeed.setOnClickListener {
            val name = edtName.text.toString()
            val status = edtStatus.text.toString()
            val foodName = edtFoodName.text.toString()
            val imageUrl = edtImageUrl.text.toString()
            val service = ClientAPI.createServiceClient()?.create(NewFeedAPI::class.java)
            val call = service?.addNewFeed(NewFeedModel(0, name, status, foodName, 0, false, imageUrl))
            call?.enqueue(object : retrofit2.Callback<NewFeedModel> {
                override fun onFailure(call: retrofit2.Call<NewFeedModel>, t: Throwable) {
                    t.message?.let { displayErrorDialog(it) }
                }

                override fun onResponse(call: retrofit2.Call<NewFeedModel>, response: Response<NewFeedModel>) {
                    (activity as RecyclerViewMainActivity).replaceFragment(NewFeedFragment())
                }
            })
        }
    }

    private fun displayErrorDialog(message: String) {
        val errorDialog = AlertDialog.Builder(requireContext())
        errorDialog.setTitle(
                getString(R.string.dialog_title_error))
                .setMessage(message)
                .setPositiveButton(R.string.dialog_text_ok) { dialog, _ -> dialog.dismiss() }
                .show()
    }
}
