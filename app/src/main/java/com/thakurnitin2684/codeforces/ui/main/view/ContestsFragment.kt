package com.thakurnitin2684.codeforces.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.ViewCompat.setNestedScrollingEnabled
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.Contest
import com.thakurnitin2684.codeforces.data.model.ParticipatedContest
import com.thakurnitin2684.codeforces.databinding.FragmentGalleryBinding
import com.thakurnitin2684.codeforces.ui.main.adapter.ContestAdapter
import com.thakurnitin2684.codeforces.ui.main.adapter.ParticipatedContestAdapter
import com.thakurnitin2684.codeforces.ui.main.viewmodel.ContestsViewModel
import com.thakurnitin2684.codeforces.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ContestsFragment : Fragment() {


    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!


    lateinit var contestAdapter: ContestAdapter
    lateinit var participatedContestAdapter: ParticipatedContestAdapter

    private val contestsViewModel: ContestsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val view = binding.root

        setNestedScrollingEnabled(view, true)


        val webView: Fragment? =
            requireActivity().supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webView is ForWebViewHome) {
            if (webView.isVisible) {
                requireActivity().supportFragmentManager.beginTransaction().remove(webView).commit()
            }
        }



        setupUI()

        setupObserver(binding)



        return view
    }


    private fun setupUI() {
        //Contests adapter inflation
        binding.upcomingContestView?.layoutManager = LinearLayoutManager(activity)
        contestAdapter = ContestAdapter(arrayListOf(), requireActivity())
        binding.upcomingContestView?.addItemDecoration(
            DividerItemDecoration(
                binding.upcomingContestView!!.context,
                (binding.upcomingContestView!!.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.upcomingContestView?.adapter = contestAdapter


        //ParticipatedContest adapter inflation
        binding.participatedContestRView?.layoutManager = LinearLayoutManager(activity)
        participatedContestAdapter = ParticipatedContestAdapter(arrayListOf(), requireActivity())
        binding.participatedContestRView?.addItemDecoration(
            DividerItemDecoration(
                binding.participatedContestRView!!.context,
                (binding.participatedContestRView!!.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.participatedContestRView?.adapter = participatedContestAdapter
    }


    private fun setupObserver(binding: FragmentGalleryBinding) {
        contestsViewModel.getContests().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { contests -> renderContestList(contests.result, binding) }
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




        contestsViewModel.getParticipatedContests().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { participatedContests ->
                        renderParticipatedContestList(
                            participatedContests.result,
                            binding
                        )
                    }
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

    private fun renderContestList(contests: List<Contest>, binding: FragmentGalleryBinding) {

        val contestTemp: ArrayList<Contest> = arrayListOf()
        for (i in 0 until 15) {
            val temp = contests[i]
            if (temp.phase == "BEFORE") {
                contestTemp.add(temp)
            }
        }
        if (contestTemp.isNotEmpty()) {
            contestTemp.reverse()
        } else {
            binding.labelUpcomingContest.text = getString(R.string.noUpcomingContest)
        }



        contestAdapter.addData(contestTemp)
        contestAdapter.notifyDataSetChanged()
    }


    private fun renderParticipatedContestList(
        participatedContest: List<ParticipatedContest>,
        binding: FragmentGalleryBinding
    ) {

        val participatedContestTemp: ArrayList<ParticipatedContest> = arrayListOf()
        binding.NoOfContests.text = participatedContest.size.toString()
        for (element in participatedContest) {
            participatedContestTemp.add(element)
        }
        participatedContestTemp.reverse()

        participatedContestAdapter.addData(participatedContestTemp)
        participatedContestAdapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
