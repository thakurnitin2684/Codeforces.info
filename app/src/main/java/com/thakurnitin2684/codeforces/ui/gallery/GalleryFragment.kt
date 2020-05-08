package com.thakurnitin2684.codeforces.ui.gallery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat.setNestedScrollingEnabled
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.thakurnitin2684.codeforces.ContestList
import com.thakurnitin2684.codeforces.ParticipatedContests
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.ui.home.ForWebViewHome
import com.thakurnitin2684.codeforces.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_gallery.*
import kotlinx.android.synthetic.main.fragment_gallery.view.*
import org.json.JSONException
import org.json.JSONObject


private const val TAG = "GalleryFragment"

class GalleryFragment : Fragment() {
    var beforeContests = ArrayList<JSONObject>()
    var patriContests = ArrayList<JSONObject>()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        setNestedScrollingEnabled(root,true)

        val webview: Fragment? = requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webview is ForWebViewHome) {
            if(webview.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webview).commit()
            }
        }

            Log.d(TAG, "contest List $ContestList")
            if (ContestList != "") {
                putContestList(JSONObject(ContestList), root)
            }
        if (ParticipatedContests != "") {
            participatedContests(JSONObject(ParticipatedContests), root)
        }

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val feedAdapter = FeedAdapter(this.requireContext(), R.layout.list_record, beforeContests)
        UpcomingContestView.adapter = feedAdapter
        val feedAdapter2 = FeedAdapter2(this.requireContext(), R.layout.participated_contest_record,patriContests)
        participatedContestView.adapter = feedAdapter2
    }
    private fun putContestList(list: JSONObject, view: View) {
        Log.d(TAG, "putContestList called")
        try {
            val resultarray = list.getJSONArray("result")
            for (i in 0 until 15) {
                val temp = resultarray.getJSONObject(i)
                if (temp.getString("phase") == "BEFORE") {
                    beforeContests.add(temp)
                }
            }
            if (beforeContests.isNotEmpty()) {
                beforeContests.reverse()
                Log.d(TAG, beforeContests.toString())
            }
            else {
                labelUpcomingContest.text = "No Upcoming Contest"
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "putItems ends")
    }
    private fun participatedContests(list: JSONObject, view: View) {
        Log.d(TAG, "participatedContests called")
        try {
            val resultarray = list.getJSONArray("result")
            view.NoOfContests.text=resultarray.length().toString()
            for (i in 0 until resultarray.length()) {
                val temp = resultarray.getJSONObject(i)
                patriContests.add(temp)
            }
                patriContests.reverse()
                Log.d(TAG, patriContests.toString())


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "participatedContests ends")
    }
}
