package com.thakurnitin2684.codeforces.ui.main.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.ParticipatedContest
import com.thakurnitin2684.codeforces.databinding.ParticipatedContestRecordBinding
import com.thakurnitin2684.codeforces.ui.main.view.ForWebViewHome
import com.thakurnitin2684.codeforces.utils.TimeDate



class ParticipatedContestAdapter(
    private val participatedContests: ArrayList<ParticipatedContest>,
    private val context: Context
) : RecyclerView.Adapter<ParticipatedContestAdapter.DataViewHolder>() {

    private lateinit var binding: ParticipatedContestRecordBinding


    class DataViewHolder(private val binding: ParticipatedContestRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(participatedContest: ParticipatedContest,context: Context) {


            val startTime: String = TimeDate.dateIs2(participatedContest.ratingUpdateTimeSeconds)
            val old = participatedContest.oldRating
            val new = participatedContest.newRating
            val rc = if (new - old >= 0) "+${(new - old)}" else "${new - old}"

            val cId = participatedContest.contestId.toString()
            binding.PRecordName2.text = participatedContest.contestName
            binding.PRecordContestId2.text = "#$cId"
            binding.POldRating2.text = old.toString()
            if (new - old >= 0) {
                binding.PRatingChange2.text = rc
                binding.PRatingChange2Red.text = ""
            } else {
                binding.PRatingChange2Red.text = rc
                binding.PRatingChange2.text = ""
            }
            binding.PRank2.text = participatedContest.rank.toString()
            binding.PUpdate.text = startTime

            binding.PButton2.setOnClickListener {
                val bndl = Bundle()
                bndl.putString("url", "https://codeforces.com/contest/$cId")
                val manager: FragmentManager =
                    (context as AppCompatActivity).supportFragmentManager
                val frag = ForWebViewHome()
                frag.arguments = bndl
                manager.beginTransaction().add(
                    R.id.nav_host_fragment, frag, "WebView"
                ).addToBackStack(null).commit()
            }



        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DataViewHolder {
        binding = ParticipatedContestRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }


    override fun getItemCount(): Int = participatedContests.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(participatedContests[position],context)

    fun addData(list: List<ParticipatedContest>) {
        participatedContests.addAll(list)
    }
}
