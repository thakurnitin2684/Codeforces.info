package com.thakurnitin2684.codeforces

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.thakurnitin2684.codeforces.ui.home.ForWebViewHome
import com.thakurnitin2684.codeforces.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.nav_header_main.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


private const val TAG = "MainActivity"
 var ContestList=""
 var ParticipatedContests=""
var  ProbSubs=""
class MainActivity : AppCompatActivity() {
    private lateinit var radioGp : RadioGroup
    private lateinit var radioBtn : RadioButton

    private lateinit var homeViewModel: HomeViewModel
  private var aboutDialog: AlertDialog?= null
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate Started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val headerView: View = navView.getHeaderView(0)
        headerView.header_handle.hint = mainHandle

        radioGp=headerView.radioButton
        if(mobileVw){
             radioGp.check(R.id.mobileView)
        }else{
            radioGp.check(R.id.desktopView)
        }

        headerView.remeber_me.isChecked = rememberKey
        if (rememberKey) {
            headerView.remeber_me.text = getString(R.string.RemeberMeOn)
            saveData()
        }
        headerView.remeber_me.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                rememberKey = true
                headerView.remeber_me.text = getString(R.string.RemeberMeOn)
                saveData()
            } else {
                rememberKey = false
                headerView.remeber_me.text = getString(R.string.RemeberMeOff)
                saveData()

            }
        }
        headerView.header_button.setOnClickListener {
            val buttonHandle= mainHandle
            val buttonObject= returnedObject
            mainHandle = headerView.header_handle.text.toString()
            if (mainHandle == "") {
                headerView.navBar_text.text = getString(R.string.nullWarning)
                mainHandle=buttonHandle
            } else {
                saveData()
                intent = Intent(this, MainActivity::class.java)
                GlobalScope.launch {
                     returnedObject= homeViewModel.downloadData(feedUrl.format(mainHandle))
                    Log.d(TAG, returnedObject)
                    withContext(Dispatchers.Main) {
                        val status: String
                        if (returnedObject != "NO") {
                            status = JSONObject(returnedObject).getString("status")
                            if (status == "OK") {

//                               val frag=HomeFragment()
//                                supportFragmentManager.beginTransaction().add(
//                                    R.id.parent,frag).commit()
                                    startActivity(intent)
                            }
                        } else {
                            headerView.navBar_text.text = getString(R.string.invalidWarning)
                            mainHandle=buttonHandle
                            returnedObject=buttonObject
                            saveData()
                        }
                    }

                }

            }
        }
        val navController = findNavController(R.id.nav_host_fragment)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_quickaccess
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)


        GlobalScope.launch {
            ContestList = homeViewModel.downloadData("https://codeforces.com/api/contest.list")
            ParticipatedContests = homeViewModel.downloadData("https://codeforces.com/api/user.rating?handle=$mainHandle")
            ProbSubs= homeViewModel.downloadData("https://codeforces.com/api/user.status?handle=$mainHandle&from=1&count=30")
        }

        Log.d(TAG, "onCreate: done")
    }
     fun checkButton(v :View) {
        val radioId = radioGp.checkedRadioButtonId
        radioBtn=findViewById(radioId)
         mobileVw = radioBtn.text=="Mobile View"
         saveData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
           when(item.itemId){
               R.id.action_about -> showAboutDialog()
           }
        return super.onOptionsItemSelected(item)
      }
    @SuppressLint("InflateParams")
    private fun showAboutDialog(){
        val messgView = layoutInflater.inflate(R.layout.about,null,false)
        val builder =AlertDialog.Builder(this)

        builder.setTitle(R.string.app_name)
        builder.setIcon(R.mipmap.ic_launcher)
        aboutDialog=builder.setView(messgView).create()
        aboutDialog?.setCanceledOnTouchOutside(true)
        val aboutVersion =messgView.findViewById(R.id.about_version) as TextView
        aboutVersion.text = BuildConfig.VERSION_NAME
        aboutDialog?.show()
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

     private fun saveData() {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val editable = sharedPref.edit()
        editable.putBoolean(REMEMBER_ME.toString(), rememberKey)
        editable.putString(MAIN_HANDLE, mainHandle)
         editable.putBoolean(VIEW_BOOL.toString(), mobileVw)
         editable.apply()
        Log.d(TAG, "hndle:${mainHandle}")
    }

    override fun onStop() {
        super.onStop()
        if(aboutDialog?.isShowing == true){
            aboutDialog?.dismiss()
        }
    }

    override fun onBackPressed() {
        val webview: Fragment? = supportFragmentManager.findFragmentByTag("HomeFragment")
        if (webview is ForWebViewHome) {
            val goback: Boolean = (webview as ForWebViewHome?)!!.canGoBack()
            if (!goback) {
                   if(webview.isVisible) {
                       supportFragmentManager.beginTransaction().remove(webview).commit()
                   }
                   }
            else (webview as ForWebViewHome?)!!.goBack()
        }

    }
}
