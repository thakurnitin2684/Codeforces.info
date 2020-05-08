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
 * Credits: timbuchalka,www.learnprogramming.academy
 */


class ViewHolder(v: View) {
    val ConName: TextView = v.findViewById(R.id.P_record_name)
    val ContestId: TextView = v.findViewById(R.id.P_record_contest_id)
    val ContestStartTime: TextView = v.findViewById(R.id.P_ratingChange)
    val ContestDuration: TextView = v.findViewById(R.id.P_rank)
    val button: Button = v.findViewById(R.id.P_button)
}

private const val TAG = "ContestAdapter"

class FeedAdapter(
    context: Context,
    private val resource: Int,
    private val applications: List<JSONObject>
) : ArrayAdapter<JSONObject>(context, resource) {     //TODO if there is an error look here


    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        Log.d(TAG, "getView called")
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
//            Log.d(TAG, "getView called with null convertView")
            view = inflater.inflate(resource, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
//            Log.d(TAG, "getView provided a convertView")
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val currentApp = applications[position]
        var startTime = "0"
        startTime = TimeDate.dateIs2(currentApp.optInt("startTimeSeconds"))
        val duration = currentApp.optInt("durationSeconds", 0)
        var drStr = ""
        if (duration != 0) {
            val mns = (duration / 60) % 60
            var hrs = ((duration / 60) - mns) / 60
            drStr = "$hrs hours :$mns mins"
            if (hrs > 24) {
                val dys = hrs / 24
                hrs = hrs - (dys * 24)
                drStr = "$dys days :$hrs hours :00 mins"
            }
        }
        val cId = currentApp.optInt("id", 0).toString()
        viewHolder.ConName.text = currentApp.optString("name", "--")
        viewHolder.ContestId.text = "#$cId"
        viewHolder.ContestStartTime.text = startTime
        viewHolder.ContestDuration.text = drStr

        viewHolder.button.setOnClickListener {
            val bndl = Bundle()
            bndl.putString("url", "https://codeforces.com/contests")
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
