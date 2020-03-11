package asiantech.internship.summer.recyclerview

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import asiantech.internship.summer.Activity
import asiantech.internship.summer.R
import kotlinx.android.synthetic.`at-uyennguyen`.fragment_add_newfeed.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewFeedFragment : Fragment() {

    companion object {
        var GALLERY_AVATAR_CODE = 101
        var GALLERY_PICTURE_CODE = 102
    }

    lateinit var imageAvatar: Uri
    lateinit var imagePicture: Uri

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_newfeed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgAvatarNF.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), GALLERY_AVATAR_CODE)
        }
        imgPictureNF.setOnClickListener {
            requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), GALLERY_PICTURE_CODE)
        }
        btnAddNewFeed.setOnClickListener {
            val name = edtName.text.toString()
            val status = edtStatus.text.toString()
            val newFeed = NewFeed()
            newFeed.id = 0
            newFeed.name = name
            newFeed.avatar = imageAvatar.toString()
            newFeed.picture = imagePicture.toString()
            newFeed.status = status
            newFeed.numberLike = 0
            newFeed.isLike = true
            val service = Retrofit.getRetrofitInstance()?.create(NewFeedAPI::class.java)
            val callNewFeed = service?.addNewFeed(newFeed)
            callNewFeed?.enqueue(object : Callback<NewFeed> {
                override fun onFailure(call: Call<NewFeed>, t: Throwable) {
                    Toast.makeText(context, "Please try again", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call<NewFeed>, response: Response<NewFeed>) {

                }

            })
            val recyclerViewFragment = NewFeedFragment()
            activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.relativeLayout, recyclerViewFragment)
                    ?.addToBackStack(null)
                    ?.commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == GALLERY_AVATAR_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_AVATAR_CODE)
            return
        }
        if (requestCode == GALLERY_PICTURE_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, GALLERY_PICTURE_CODE)
            return
        } else {
            if (!this.shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Toast.makeText(context, "Please open permission on settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GALLERY_AVATAR_CODE && resultCode == Activity.RESULT_OK) {
            imageAvatar = data?.data!!
            imgAvatarNF.setImageURI(imageAvatar)
        }
        if (requestCode == GALLERY_PICTURE_CODE && resultCode == Activity.RESULT_OK) {
            imagePicture = data?.data!!
            imgPictureNF.setImageURI(imagePicture)
        }
    }
}
