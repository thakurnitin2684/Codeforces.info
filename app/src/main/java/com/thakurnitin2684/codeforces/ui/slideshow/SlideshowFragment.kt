package com.thakurnitin2684.codeforces.ui.slideshow

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.thakurnitin2684.codeforces.ProbSubs
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.ui.home.ForWebViewHome
import kotlinx.android.synthetic.main.fragment_slideshow.*
import org.json.JSONException
import org.json.JSONObject

private const val TAG = "SlideShowFrag"

class SlideshowFragment : Fragment() {
    var previousS = ArrayList<JSONObject>()
    private lateinit var slideshowViewModel: SlideshowViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        slideshowViewModel =
            ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)

        val webview: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webview is ForWebViewHome) {
            if (webview.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webview).commit()
            }
        }

        Log.d(TAG, "ProbSubs: $ProbSubs")
        if (ProbSubs != "") {
            prevSubs(JSONObject(ProbSubs), root)
        }
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val subAdapter = SubAdapter(this.requireContext(), R.layout.last_subs_record, previousS)
        RecentSubListView.adapter = subAdapter
    }

    private fun prevSubs(list: JSONObject, view: View) {
        Log.d(TAG, "participatedContests called")
        try {
            val resultarray = list.getJSONArray("result")
            for (i in 0 until resultarray.length()) {
                val temp = resultarray.getJSONObject(i)
                previousS.add(temp)
            }
            Log.d(TAG, previousS.toString())


        } catch (e: JSONException) {
            e.printStackTrace()
        }
        Log.d(TAG, "participatedContests ends")
    }
}
