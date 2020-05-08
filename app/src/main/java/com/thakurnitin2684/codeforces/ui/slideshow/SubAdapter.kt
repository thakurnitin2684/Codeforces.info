package com.thakurnitin2684.codeforces.ui.slideshow


import android.content.Context
import android.os.Bundle
import android.util.Log
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


class ViewHolder3(v: View) {
    val subId: TextView = v.findViewById(R.id.Sub_Id)
    val ContestId: TextView = v.findViewById(R.id.contestID)
    val time: TextView = v.findViewById(R.id.noteTime)
    val probName: TextView = v.findViewById(R.id.ProblemName)
    val okVer: TextView = v.findViewById(R.id.OKverdict)
    val waVer: TextView = v.findViewById(R.id.WAverdict)
    val lang:TextView=v.findViewById(R.id.ProgLang)
    val tc:TextView=v.findViewById(R.id.TimeConsumed)
    val mc:TextView=v.findViewById(R.id.MemConsumed)
    val button:Button=v.findViewById(R.id.viewButtonProb)
}

private const val TAG = "SubAdapter"
class SubAdapter(context: Context, private val resource: Int, private val applications: List<JSONObject>)
    : ArrayAdapter<JSONObject>(context, resource) {


    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        Log.d(TAG, "getView called")
        val view: View
        val viewHolder3: ViewHolder3

        if (convertView == null) {
//            Log.d(TAG, "getView called with null convertView")
            view = inflater.inflate(resource, parent, false)
            viewHolder3 = ViewHolder3(view)
            view.tag = viewHolder3
        } else {
//            Log.d(TAG, "getView provided a convertView")
            view = convertView
            viewHolder3 = view.tag as ViewHolder3
        }

        val currentApp = applications[position]
        var startTime ="0"
        startTime=TimeDate.dateIs2(currentApp.optInt("creationTimeSeconds"))
        val verdict = currentApp.optString("verdict","--")
        val sId=currentApp.optString("id","")
        val cId=currentApp.optInt("contestId",0).toString()

        viewHolder3.subId.text = "#$sId"
        viewHolder3.ContestId.text = "of #$cId"
        viewHolder3.time.text = startTime
        if(verdict=="OK") {
            viewHolder3.okVer.text = verdict
            viewHolder3.waVer.text=""
        }else{
            viewHolder3.okVer.text=""
            viewHolder3.waVer.text=verdict
        }
        viewHolder3.lang.text =  currentApp.optString("programmingLanguage","--")
        viewHolder3.tc.text=currentApp.optInt("timeConsumedMillis",0).toString()+" ms"
        viewHolder3.mc.text=(currentApp.optInt("memoryConsumedBytes",0)/1000).toString()+"KB"
        val problem=currentApp.optJSONObject("problem")
        Log.d(TAG,"problem :$problem")
        viewHolder3.probName.text=problem.optString("name","--")

        viewHolder3.button.setOnClickListener{
            val bndl = Bundle()
            bndl.putString("url","https://codeforces.com/contest/$cId/submission/$sId")
            val manager: FragmentManager =
                (context as AppCompatActivity).supportFragmentManager
            val frag= ForWebViewHome()
            frag.arguments=bndl
            manager.beginTransaction().replace(
                R.id.nav_host_fragment,frag,"HomeFragment").addToBackStack(null).commit()
        }
        return view
    }

    override fun getCount(): Int {
//        Log.d(TAG, "getCount called")
        return applications.size
    }
}
