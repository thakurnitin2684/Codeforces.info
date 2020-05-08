package com.thakurnitin2684.codeforces.ui.quickaccess

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.mainHandle
import com.thakurnitin2684.codeforces.ui.home.ForWebViewHome
import kotlinx.android.synthetic.main.fragment_quick.view.*

private const val TAG = "QuickFragment"

class QuickFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Log.d(TAG, "onCreateView started")
        val root = inflater.inflate(R.layout.fragment_quick, container, false)

        val webview: Fragment? = requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webview is ForWebViewHome) {
            if(webview.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webview).commit()
            }
        }

        root.qaHome.setOnClickListener {
            callWeb("https://codeforces.com/")
        }
        root.qaContests.setOnClickListener {
            callWeb("https://codeforces.com/contests")
        }
        root.qaRating.setOnClickListener {
            callWeb("https://codeforces.com/ratings")
        }
        root.qaFriends.setOnClickListener{
            callWeb("https://codeforces.com/friends")
        }
        root.qaSettings.setOnClickListener{
            callWeb("https://codeforces.com/settings/general")
        }
        root.qaBlogEntries.setOnClickListener{
            callWeb("https://codeforces.com/blog/$mainHandle")
        }
        root.aqcommentt.setOnClickListener{
            callWeb("https://codeforces.com/comments/with/$mainHandle")
        }
        root.qaUserTalk.setOnClickListener{
            callWeb("https://codeforces.com/usertalk")
        }
        root.qateams.setOnClickListener{
            callWeb("https://codeforces.com/teams/with/$mainHandle")
        }
        root.qaApi.setOnClickListener{
            callWeb("https://codeforces.com/apiHelp")
        }
        root.qaCalender.setOnClickListener{
            callWeb("https://codeforces.com/calendar")
        }
        return root
    }

    private fun callWeb(url: String) {
        val bndl = Bundle()
        bndl.putString("url", url)
        val frag = ForWebViewHome()
        frag.arguments = bndl

        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment, frag, "HomeFragment"
        ).addToBackStack(null).commit()
    }
}