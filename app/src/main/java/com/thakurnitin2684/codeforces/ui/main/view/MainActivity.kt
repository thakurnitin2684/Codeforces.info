package com.thakurnitin2684.codeforces.ui.main.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.thakurnitin2684.codeforces.BaseApplication
import com.thakurnitin2684.codeforces.BuildConfig
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.Home
import com.thakurnitin2684.codeforces.data.repository.PrefRepository
import com.thakurnitin2684.codeforces.databinding.ActivityMainBinding
import com.thakurnitin2684.codeforces.databinding.NavHeaderMainBinding
import com.thakurnitin2684.codeforces.ui.main.viewmodel.HomeViewModel
import com.thakurnitin2684.codeforces.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    //homeViewModel instance which will be used in fragment also
    companion object {
        lateinit var homeViewModel: HomeViewModel
    }
    private val   prefRepository by lazy { PrefRepository(applicationContext) }

    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var navHeaderBinding: NavHeaderMainBinding

    private lateinit var radioGp : RadioGroup
    private lateinit var radioBtn : RadioButton


    private var aboutDialog: AlertDialog?= null
    private lateinit var appBarConfiguration: AppBarConfiguration


    private lateinit var baseContext : BaseApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        val mainView = activityMainBinding.root
        setContentView(mainView)

        baseContext = (this.application as BaseApplication)


        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val homeViewModell by viewModels<HomeViewModel>()
        homeViewModel = homeViewModell

        homeViewModel.fetchHomeInfo( baseContext.getMainHandle())


        val headerView: View = activityMainBinding.navView.getHeaderView(0)
        navHeaderBinding = NavHeaderMainBinding.bind(headerView)

        navHeaderBinding.headerHandle.hint=  baseContext.getMainHandle()


        //Radio button in navigation header
        radioGp=navHeaderBinding.radioButton
        if(baseContext.getMobileView()){
             radioGp.check(R.id.mobileView)
        }else{
            radioGp.check(R.id.desktopView)
        }

         navHeaderBinding.rememberMe.isChecked = baseContext.getRememberKey()

        if (baseContext.getRememberKey()) {
            navHeaderBinding.rememberMe.text = getString(R.string.RemeberMeOn)
        }


        navHeaderBinding.rememberMe.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                baseContext.setRememberKey(true)
                navHeaderBinding.rememberMe.text = getString(R.string.RemeberMeOn)
            } else {
                baseContext.setRememberKey(false)
                navHeaderBinding.rememberMe.text = getString(R.string.RemeberMeOff)

            }

            prefRepository.setRememberMe(baseContext.getRememberKey())

        }
        navHeaderBinding.headerButton.setOnClickListener {
             baseContext.setMainHandle(navHeaderBinding.headerHandle.text.toString())
            if ( baseContext.getMainHandle() == "") {
                navHeaderBinding.navBarText.text = getString(R.string.nullWarning)
            } else {


                homeViewModel.fetchHomeInfo( baseContext.getMainHandle())
                setupLoginObserver()
                intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            }
        }


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment?

        if (navHostFragment != null) {
            val navController = navHostFragment.navController

            // Setup NavigationUI here
            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_quickaccess
                ), activityMainBinding.drawerLayout
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            activityMainBinding.navView.setupWithNavController(navController)
        }
    }


    private fun setupLoginObserver() {

        homeViewModel.getHomeInfo().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    navHeaderBinding.navBarText.text = getString(R.string.invalidWarning)
                    it.data?.let { homeInfo ->
                        renderList(homeInfo)
                        navHeaderBinding.navBarText.visibility = View.GONE
                    }
                }
                Status.LOADING -> {
                    navHeaderBinding.navBarText.text = getString(R.string.waiting)
                }
                Status.ERROR -> {
                    navHeaderBinding.navBarText.text = getString(R.string.invalidWarning)
                }
            }
        }
    }



    private fun renderList(homeInfo: Home) {
        val status = homeInfo.status

        if (status == "OK") {

            prefRepository.setMainHandle(baseContext.getMainHandle())

            navHeaderBinding.headerHandle.hint=  baseContext.getMainHandle()
            navHeaderBinding.headerHandle.text.clear()

//            To close the drawer
            activityMainBinding.drawerLayout.close()

            //To close the keyboard
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

    }



     fun checkButton(view: View) {
        val radioId = radioGp.checkedRadioButtonId
        radioBtn=findViewById(radioId)
         baseContext.setMobileView( radioBtn.text=="Mobile View")
         prefRepository.setViewBool(baseContext.getMobileView())

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


    override fun onStop() {
        super.onStop()
        if(aboutDialog?.isShowing == true){
            aboutDialog?.dismiss()
        }
    }

    override fun onBackPressed() {
        val webview: Fragment? = supportFragmentManager.findFragmentByTag("WebView")
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
