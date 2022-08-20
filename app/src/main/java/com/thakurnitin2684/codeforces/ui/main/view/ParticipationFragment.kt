package com.thakurnitin2684.codeforces.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurnitin2684.codeforces.data.model.RecentSubmission
import com.thakurnitin2684.codeforces.databinding.FragmentSlideshowBinding
import com.thakurnitin2684.codeforces.ui.main.viewmodel.ParticipationViewModel
import com.thakurnitin2684.codeforces.ui.slideshow.ParticipationAdapter
import com.thakurnitin2684.codeforces.utils.Status
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ParticipationFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private val binding get() = _binding!!


    lateinit var participationAdapter: ParticipationAdapter

    private val participationViewModel: ParticipationViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val view = binding.root


        val webView: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webView is ForWebViewHome) {
            if (webView.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webView).commit()
            }
        }


        setupUI()

        setupObserver()

        return view
    }

    private fun setupUI() {

        binding.recentSubListView?.layoutManager = LinearLayoutManager(activity)
        participationAdapter = ParticipationAdapter(arrayListOf(),requireActivity())
        binding.recentSubListView?.addItemDecoration(
            DividerItemDecoration(
                binding.recentSubListView!!.context,
                (binding.recentSubListView!!.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recentSubListView?.adapter = participationAdapter


    }

    private fun setupObserver() {
        participationViewModel.getRecentSubmissions().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { recentSub -> renderContestList(recentSub.result) }
                }
                Status.LOADING -> {
                    Toast.makeText(
                        requireActivity(), "Loading ....",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                Status.ERROR -> {
                    Toast.makeText(requireActivity(), it.message, Toast.LENGTH_LONG).show()
                }
            }
        }


    }


    private fun renderContestList(
        recentSubmission: List<RecentSubmission>,
    ) {

        participationAdapter.addData(recentSubmission)
        participationAdapter.notifyDataSetChanged()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
