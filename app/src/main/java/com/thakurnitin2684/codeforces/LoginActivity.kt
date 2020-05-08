package com.thakurnitin2684.codeforces

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.thakurnitin2684.codeforces.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.loading.*
import kotlinx.android.synthetic.main.login_page.*
import kotlinx.coroutines.*
import org.json.JSONObject

private const val TAG = "LoginActivity"

internal const val MAIN_HANDLE= ""
internal const val REMEMBER_ME= false
internal const val VIEW_BOOL= true
  var mainHandle: String = ""
 var feedUrl = "https://codeforces.com/api/user.info?handles=%s"
  var returnedObject: String = ""
  var rememberKey: Boolean=false
var mobileVw:Boolean = true
class LoginActivity : AppCompatActivity() {
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        rememberKey = sharedPref.getBoolean(REMEMBER_ME.toString(), false)
        mainHandle = sharedPref.getString(MAIN_HANDLE, "").toString()
        mobileVw = sharedPref.getBoolean(VIEW_BOOL.toString(), true)

        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        if (rememberKey) {
            setContentView(R.layout.loading)
            reEnterButton.visibility=View.GONE
            Toast.makeText(this, " $mainHandle ", Toast.LENGTH_SHORT).show()
            intent = Intent(this, MainActivity::class.java)

            GlobalScope.launch {
                returnedObject = homeViewModel.downloadData(feedUrl.format(mainHandle))
                Log.d(TAG, returnedObject)
                withContext(Dispatchers.Main) {
                    if (returnedObject != "NO") {
                        startActivity(intent)
                    }else{
                        loading_progressBar.visibility=View.GONE
                        loading_text.text=" ${getString(R.string.internetError)}"
                        reEnterButton.visibility=View.VISIBLE

                    }
                }
            }
            reEnterButton.setOnClickListener{
                rememberKey=false
                intent = Intent(this, LoginActivity::class.java)
                saveData2()
                startActivity(intent)

            }
        } else {
            setContentView(R.layout.login_page)
            internetLabel.visibility=View.GONE

            login_remember.setOnCheckedChangeListener { _, isChecked ->
                rememberKey = isChecked
            }
            progressBar.visibility = View.GONE
            Log.d(TAG, "onCreate Started")

            loginButton.setOnClickListener {
                internetLabel.visibility=View.GONE
                mainHandle = InputHandle.editText?.text.toString()
                if (mainHandle == "") {
                    Status.text = getString(R.string.nullWarning)
                } else {


                    GlobalScope.launch {
                        returnedObject = homeViewModel.downloadData(feedUrl.format(mainHandle))
                        Log.d(TAG, returnedObject)
                        withContext(Dispatchers.Main) {
//                        homeViewModel.showdata()
                            openMA()
                        }
                    }
                    progressBar.visibility = View.VISIBLE
                    Status.text = getString(R.string.waiting)
                }
            }
        }
        Log.d(TAG, "onCreate Done")
    }

    private fun openMA() {
        var status= ""
        if (returnedObject != "NO") {
            status = JSONObject(returnedObject).getString("status")
            Log.d(TAG, "status:$status")
         if (status == "OK") {
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            Status.text = getString(R.string.invalidWarning)
            progressBar.visibility = View.GONE
        }
    }else
    {
        Status.text = getString(R.string.invalidWarning)
        progressBar.visibility = View.GONE
        internetLabel.visibility=View.VISIBLE
    }
 }
    private fun saveData2() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editable = sharedPref.edit()
        editable.putBoolean(REMEMBER_ME.toString(), rememberKey)
        editable.apply()
    }
}