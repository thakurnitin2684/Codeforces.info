package com.thakurnitin2684.codeforces.ui.gallery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.TimeDate
import com.thakurnitin2684.codeforces.ui.home.ForWebViewHome
import org.json.JSONObject

/**
 * Credits: timbuchalka,  www.learnprogramming.academy
 */


class ViewHolder2(v: View) {
    val ConName: TextView = v.findViewById(R.id.P_record_name2)
    val ContestId: TextView = v.findViewById(R.id.P_record_contest_id2)
    val ratingChange: TextView = v.findViewById(R.id.P_ratingChange2)
    val ratingChange2: TextView = v.findViewById(R.id.P_ratingChange2Red)
    val Contestrank: TextView = v.findViewById(R.id.P_rank2)
    val ContesetOldRating: TextView = v.findViewById(R.id.P_oldRating2)
    val updateDate: TextView = v.findViewById(R.id.P_update)
    val button: Button = v.findViewById(R.id.P_button_2)
}

private const val TAG = "ContestAdapter"

class FeedAdapter2(
    context: Context,
    private val resource: Int,
    private val applications: List<JSONObject>
) : ArrayAdapter<JSONObject>(context, resource) {     //TODO if there is an error look here


    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        Log.d(TAG, "getView called")
        val view: View
        val viewHolder2: ViewHolder2

        if (convertView == null) {
//            Log.d(TAG, "getView called with null convertView")
            view = inflater.inflate(resource, parent, false)
            viewHolder2 = ViewHolder2(view)
            view.tag = viewHolder2
        } else {
//            Log.d(TAG, "getView provided a convertView")
            view = convertView
            viewHolder2 = view.tag as ViewHolder2
        }

        val currentApp = applications[position]
        var startTime = "0"
        startTime = TimeDate.dateIs2(currentApp.optInt("ratingUpdateTimeSeconds"))
        val old = currentApp.optInt("oldRating", 0)
        val new = currentApp.optInt("newRating", 0)
        val rc = if (new - old >= 0) "+${(new - old)}" else "${new - old}"

        val cId = currentApp.optInt("contestId", 0).toString()
        viewHolder2.ConName.text = currentApp.optString("contestName", "--")
        viewHolder2.ContestId.text = "#$cId"
        viewHolder2.ContesetOldRating.text = old.toString()
        if (new - old >= 0) {
            viewHolder2.ratingChange.text = rc
            viewHolder2.ratingChange2.text = ""
        } else {
            viewHolder2.ratingChange2.text = rc
            viewHolder2.ratingChange.text = ""
        }
        viewHolder2.Contestrank.text = currentApp.optInt("rank", 0).toString()
        viewHolder2.updateDate.text = startTime

        viewHolder2.button.setOnClickListener {
            val bndl = Bundle()
            bndl.putString("url", "https://codeforces.com/contest/$cId")
            val manager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val frag = ForWebViewHome()
            frag.arguments = bndl
            manager.beginTransaction().replace(
                R.id.nav_host_fragment, frag, "HomeFragment"
            ).addToBackStack(null).commit()
        }
        return view
    }

    override fun getCount(): Int {
//        Log.d(TAG, "getCount called")
        return applications.size
    }
}
