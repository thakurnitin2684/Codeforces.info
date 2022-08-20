package com.thakurnitin2684.codeforces.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.thakurnitin2684.codeforces.BaseApplication
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.databinding.FragmentQuickBinding

class QuickAccessFragment : Fragment() {

    private var _binding: FragmentQuickBinding? = null
    private val binding get() = _binding!!
    private lateinit var baseContext : BaseApplication


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuickBinding.inflate(inflater, container, false)
        val view = binding.root

        baseContext = (requireActivity().application as BaseApplication)



       val webView: Fragment? = requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webView is ForWebViewHome) {
            if(webView.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webView).commit()
            }
        }

        binding.qaHome.setOnClickListener {
            callWeb("https://codeforces.com/")
        }
        binding.qaContests.setOnClickListener {
            callWeb("https://codeforces.com/contests")
        }
        binding.qaRating.setOnClickListener {
            callWeb("https://codeforces.com/ratings")
        }
        binding.qaFriends.setOnClickListener{
            callWeb("https://codeforces.com/friends")
        }
        binding.qaSettings.setOnClickListener{
            callWeb("https://codeforces.com/settings/general")
        }
        binding.qaBlogEntries.setOnClickListener{
            callWeb("https://codeforces.com/blog/${baseContext.getMainHandle()}")
        }
        binding.aqcommentt.setOnClickListener{
            callWeb("https://codeforces.com/comments/with/${baseContext.getMainHandle()}")
        }
        binding.qaUserTalk.setOnClickListener{
            callWeb("https://codeforces.com/usertalk")
        }
        binding.qateams.setOnClickListener{
            callWeb("https://codeforces.com/teams/with/${baseContext.getMainHandle()}")
        }
        binding.qaApi.setOnClickListener{
            callWeb("https://codeforces.com/apiHelp")
        }
        binding.qaCalender.setOnClickListener{
            callWeb("https://codeforces.com/calendar")
        }
        return view
    }
    private fun callWeb(url: String) {
        val bndl = Bundle()
        bndl.putString("url", url)
        val frag = ForWebViewHome()
        frag.arguments = bndl

        requireActivity().supportFragmentManager.beginTransaction().add(
            R.id.nav_host_fragment, frag, "WebView"
        ).addToBackStack(null).commit()
    }

}