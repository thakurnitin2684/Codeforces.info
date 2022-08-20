package com.thakurnitin2684.codeforces.ui.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.squareup.picasso.Picasso
import com.thakurnitin2684.codeforces.BaseApplication
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.Home
import com.thakurnitin2684.codeforces.databinding.FragmentHomeBinding
import com.thakurnitin2684.codeforces.ui.main.view.MainActivity.Companion.homeViewModel
import com.thakurnitin2684.codeforces.ui.main.viewmodel.ContestsViewModel
import com.thakurnitin2684.codeforces.ui.main.viewmodel.ParticipationViewModel
import com.thakurnitin2684.codeforces.utils.Status
import com.thakurnitin2684.codeforces.utils.TimeDate
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private val contestsViewModel: ContestsViewModel by activityViewModels()
    private val participationViewModel: ParticipationViewModel by activityViewModels()

    private lateinit var baseContext : BaseApplication


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root


        val webView: Fragment? = requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webView is ForWebViewHome) {
                if(webView.isVisible) {
                    requireActivity().supportFragmentManager.beginTransaction().remove(webView).commit()
                }
        }

        baseContext = (requireActivity().application as BaseApplication)

        //Fetching data for all fragments
        contestsViewModel.fetchContests()
        contestsViewModel.fetchParticipatedContests(baseContext.getMainHandle())
        participationViewModel.fetchRecentSubmissions(baseContext.getMainHandle())

        setupObserver(binding)

        return view
    }


    private fun setupObserver(binding: FragmentHomeBinding) {
        homeViewModel.getHomeInfo().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { homeInfo -> renderList(homeInfo, binding)
                    }
                }
                Status.LOADING -> {
                    Toast.makeText(
                        requireActivity(), "Loading ....",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.ERROR -> {
                    //Handle Error
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }





    private fun renderList(homeInfo: Home,binding: FragmentHomeBinding) {

            val userInfo = homeInfo.result[0]

            val titlePhoto = userInfo.titlePhoto
            val dateString= TimeDate.dateIs(userInfo.lastOnlineTimeSeconds)
            val dateString2= TimeDate.dateIs(userInfo.registrationTimeSeconds)


        binding.homeHandle.text = baseContext.getMainHandle()
        val name =  userInfo.firstName+" " + userInfo.lastName
        binding.homeName.text =name
        binding.homeRank.text = userInfo.rank
        binding.homeRating.text = userInfo.rating.toString()
        binding.homeMaxRank.text = userInfo.maxRank
        binding.homeMaxRating.text = userInfo.maxRating.toString()
        binding.homeCountry.text=userInfo.country.toString()
        binding.homeOrgztn.text=userInfo.organization.toString()
        binding.homeFriendof.text=userInfo.friendOfCount.toString()
        binding.homeCity.text=userInfo.city
        binding.homeLastOnline.text=dateString
        binding.homeContribution.text=userInfo.contribution.toString()
        binding.labelParticipated.text=dateString2

            val transformation= RoundedCornersTransformation(15,0)
            if (titlePhoto != "--") {
                Picasso.get().load(titlePhoto).error(R.drawable.avatar)
                    .placeholder(R.drawable.avatar).transform(transformation).into(binding.homeAvatar)
            }

            binding.viewButton.setOnClickListener {

                val bndl =Bundle()
                bndl.putString("url","https://codeforces.com/profile/${baseContext.getMainHandle()}")
                val frag= ForWebViewHome()
                frag.arguments=bndl

                requireActivity().supportFragmentManager.beginTransaction().add(
                            R.id.nav_host_fragment,frag, "WebView").addToBackStack(null).commit()
            }
       }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


