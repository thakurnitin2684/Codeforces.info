package com.thakurnitin2684.codeforces.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.squareup.picasso.Picasso
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.TimeDate
import com.thakurnitin2684.codeforces.mainHandle
import com.thakurnitin2684.codeforces.returnedObject
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.json.JSONException
import org.json.JSONObject


private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {


    private var feedUrl = "https://codeforces.com/api/user.info?handles=%s"
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView started")
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val webview: Fragment? = requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webview is ForWebViewHome) {
                if(webview.isVisible) {
                    requireActivity().supportFragmentManager.beginTransaction().remove(webview).commit()
                }
        }

        putItems(JSONObject(returnedObject), root)

        return root
    }

    private fun putItems(userData: JSONObject, view: View) {
        Log.d(TAG, "putItems called")
        try {

            val resultarray = userData.getJSONArray("result")
            val userInfo = resultarray.getJSONObject(0)
            val fname = userInfo.optString("firstName", "--")
            val lname = userInfo.optString("lastName", "--")
            val country = userInfo.optString("country", "--")
            val city = userInfo.optString("city", "--")
            val organization = userInfo.optString("organization", "--")
            val contribution = userInfo.optInt("contribution", 0)
            val rank = userInfo.optString("rank", "--")
            val rating = userInfo.optInt("rating", 0)
            val maxRank = userInfo.optString("maxRank", "--")
            val maxRating = userInfo.optInt("maxRating", 0)
            val lastOnlineTimeSeconds = userInfo.optInt("lastOnlineTimeSeconds", 0)
            val registrationTimeSeconds = userInfo.optInt("registrationTimeSeconds", 0)
            val friendOfCount = userInfo.optInt("friendOfCount", 0)
            val titlePhoto = "https:" + userInfo.optString("titlePhoto", "--")

            val dateString= TimeDate.dateIs(lastOnlineTimeSeconds).toString()
            val dateString2= TimeDate.dateIs(registrationTimeSeconds).toString()


            view.home_handle.text = mainHandle
            view.home_name.text = fname+" " + lname
            view.home_rank.text = rank
            view.home_rating.text = rating.toString()
            view.home_maxRank.text = maxRank
            view.home_maxRating.text = maxRating.toString()
            view.home_country.text=country.toString()
            view.home_orgztn.text=organization.toString()
            view.home_friendof.text=friendOfCount.toString()
            view.home_city.text=city
            view.home_lastOnline.text=dateString
            view.home_contribution.text=contribution.toString()
            view.labelParticipated.text=dateString2
            val transformation= RoundedCornersTransformation(15,0)
            if (titlePhoto != "--") {
                Picasso.get().load(titlePhoto).error(R.drawable.avatar)
                    .placeholder(R.drawable.avatar).transform(transformation).into(view.home_avatar)
            }

            view.viewButton.setOnClickListener {

                val bndl =Bundle()
                bndl.putString("url","https://codeforces.com/profile/$mainHandle")
                val frag=ForWebViewHome()
                frag.arguments=bndl

                                requireActivity().supportFragmentManager.beginTransaction().replace(
                                    R.id.nav_host_fragment,frag, TAG).addToBackStack(null).commit()
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "putItems ends")
    }

}


