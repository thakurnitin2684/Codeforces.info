package com.thakurnitin2684.codeforces.ui.main.adapter

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.Contest
import com.thakurnitin2684.codeforces.databinding.ListRecordBinding
import com.thakurnitin2684.codeforces.ui.main.view.ForWebViewHome
import com.thakurnitin2684.codeforces.utils.TimeDate



class ContestAdapter(
    private val contests: ArrayList<Contest>,
    private val context: Context
) : RecyclerView.Adapter<ContestAdapter.DataViewHolder>() {

    private lateinit var binding: ListRecordBinding



    class DataViewHolder(private val binding: ListRecordBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(contest: Contest,context: Context) {


            val startTime: String = TimeDate.dateIs2(contest.startTimeSeconds)
            val duration = contest.durationSeconds
            var drStr = ""
            if (duration != 0) {
                val mns = (duration / 60) % 60
                var hrs = ((duration / 60) - mns) / 60
                drStr = "$hrs hours :$mns mins"
                if (hrs > 24) {
                    val dys = hrs / 24
                    hrs -= (dys * 24)
                    drStr = "$dys days :$hrs hours :00 mins"
                }
            }
            val cId = contest.id.toString()
            binding.PRecordName.text = contest.name
            binding.PRecordContestId.text = "#$cId"
            binding.PRatingChange.text = startTime
            binding.PRank.text = drStr

            binding.PButton.setOnClickListener {


                val bndl = Bundle()
                bndl.putString("url", "https://codeforces.com/contests")
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : DataViewHolder{
        binding = ListRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)

    }


    override fun getItemCount(): Int = contests.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(contests[position],context)

    fun addData(list: List<Contest>) {
        contests.addAll(list)
    }


}
