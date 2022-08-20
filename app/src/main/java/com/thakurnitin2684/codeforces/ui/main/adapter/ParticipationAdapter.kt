package com.thakurnitin2684.codeforces.ui.slideshow


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.RecentSubmission
import com.thakurnitin2684.codeforces.databinding.LastSubsRecordBinding
import com.thakurnitin2684.codeforces.ui.main.view.ForWebViewHome
import com.thakurnitin2684.codeforces.utils.TimeDate


class ParticipationAdapter(
    private val recentSubmission: ArrayList<RecentSubmission>,
    private val context: Context
) : RecyclerView.Adapter<ParticipationAdapter.DataViewHolder>() {

    private lateinit var binding: LastSubsRecordBinding


    class DataViewHolder(private val binding: LastSubsRecordBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recentSubmission: RecentSubmission,context: Context) {

            val startTime: String = TimeDate.dateIs2(recentSubmission.creationTimeSeconds)
            val verdict = recentSubmission.verdict
            val sId = recentSubmission.id
            val cId = recentSubmission.contestId.toString()

            binding.SubId.text = "#$sId"
            binding.contestID.text = "of #$cId"
            binding.noteTime.text = startTime
            if (verdict == "OK") {
                binding.OKverdict.text = verdict
                binding.WAverdict.text = ""
            } else {
                binding.OKverdict.text = ""
                binding.WAverdict.text = verdict
            }
            binding.ProgLang.text = recentSubmission.programmingLanguage
            binding.TimeConsumed.text = recentSubmission.timeConsumedMillis.toString() + " ms"
            binding.MemConsumed.text = (recentSubmission.memoryConsumedBytes / 1000).toString() + "KB"
            val problem = recentSubmission.problem
            binding.ProblemName.text = problem.name

            binding.viewButtonProb.setOnClickListener {
                val bndl = Bundle()
                bndl.putString("url", "https://codeforces.com/contest/$cId/submission/$sId")
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


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DataViewHolder {
        binding =
            LastSubsRecordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }


    override fun getItemCount(): Int = recentSubmission.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(recentSubmission[position],context)

    fun addData(list: List<RecentSubmission>) {
        recentSubmission.addAll(list)
    }





}
