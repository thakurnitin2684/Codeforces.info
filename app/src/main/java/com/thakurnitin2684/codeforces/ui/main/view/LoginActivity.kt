package com.thakurnitin2684.codeforces.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.thakurnitin2684.codeforces.BaseApplication
import com.thakurnitin2684.codeforces.R
import com.thakurnitin2684.codeforces.data.model.Home
import com.thakurnitin2684.codeforces.data.repository.PrefRepository
import com.thakurnitin2684.codeforces.databinding.LoadingBinding
import com.thakurnitin2684.codeforces.databinding.LoginPageBinding
import com.thakurnitin2684.codeforces.ui.main.viewmodel.HomeViewModel
import com.thakurnitin2684.codeforces.utils.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var loadingBinding: LoadingBinding
    private lateinit var loginBinding: LoginPageBinding


    private val   prefRepository by lazy { PrefRepository(applicationContext) }

    private val homeViewModel: HomeViewModel by viewModels()

     private lateinit var baseContext  : BaseApplication


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingBinding = LoadingBinding.inflate(layoutInflater)
        val loadingView = loadingBinding.root

        loginBinding = LoginPageBinding.inflate(layoutInflater)
        val loginView = loginBinding.root



        baseContext = (this.application as BaseApplication)


        //Getting saved values from Shared Preferences and
        // putting them in global variables declared in BaseApplication
        baseContext.setRememberKey(prefRepository.getRememberMe(false))
        baseContext.setMainHandle(prefRepository.getMainHandle())
        baseContext.setMobileView(prefRepository.getViewBool(false))



        //If remember button is on , then load and go to main activity directly
        if (baseContext.getRememberKey()) {
            setContentView(loadingView)
            loadingBinding.reEnterButton.visibility = View.GONE


            Toast.makeText(this, " ${baseContext.getMainHandle()} ", Toast.LENGTH_SHORT).show()


            //Fetch details of user
            homeViewModel.fetchHomeInfo( baseContext.getMainHandle())

            setupLoadingObserver()


            loadingBinding.reEnterButton.setOnClickListener {

                baseContext.setRememberKey(false)
                intent = Intent(this, LoginActivity::class.java)
                prefRepository.setRememberMe(baseContext.getRememberKey())
                startActivity(intent)

            }
        } else {
            // if remember key is off then enter handle and go to Main Activity
            setContentView(loginView)

            loginBinding.internetLabel.visibility = View.GONE
            loginBinding.progressBar.visibility = View.GONE


            loginBinding.loginRemember.setOnCheckedChangeListener { _, isChecked ->
                baseContext.setRememberKey(isChecked)

            }

            loginBinding.loginButton.setOnClickListener {
                loginBinding.internetLabel.visibility = View.GONE

                baseContext.setMainHandle(loginBinding.InputHandle.editText?.text.toString())
                if (baseContext.getMainHandle() == "") {
                    loginBinding.Status.text = getString(R.string.nullWarning)
                } else {
                    homeViewModel.fetchHomeInfo(baseContext.getMainHandle())
                    setupLoginObserver()
                }
            }
        }
    }

    private fun setupLoadingObserver() {

        homeViewModel.getHomeInfo().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("Login", "success")

                    loadingBinding.loadingProgressBar.visibility = View.GONE
                    it.data?.let { homeInfo ->
                        renderList(homeInfo)
                    }

                }
                Status.LOADING -> {
                    Log.d("Login", "Laoding")
                    loadingBinding.loadingProgressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.d("Login", "error")

                    loadingBinding.loadingProgressBar.visibility = View.GONE
                    loadingBinding.loadingText.text = " ${getString(R.string.internetError)}"
                    loadingBinding.reEnterButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupLoginObserver() {

        homeViewModel.getHomeInfo().observe(this) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.d("Login", "success")

                    //if the handle is invalid , the api call will be successfull but homeInfo will be empty
                    loginBinding.Status.text = getString(R.string.invalidWarning)

                    loginBinding.progressBar.visibility = View.GONE
                    it.data?.let { homeInfo ->
                        renderList(homeInfo)
                        loginBinding.Status.visibility = View.GONE
                    }
                }
                Status.LOADING -> {
                    Log.d("Login", "Laoding")

                    loginBinding.Status.text = getString(R.string.waiting)
                    loginBinding.progressBar.visibility = View.VISIBLE
                }
                Status.ERROR -> {
                    Log.d("Login", "error")

                    loginBinding.progressBar.visibility = View.GONE
                    loginBinding.internetLabel.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun renderList(homeInfo: Home) {
        val status = homeInfo.status

        //if data is loaded then go to main activity
        if (status == "OK") {
            prefRepository.setRememberMe(baseContext.getRememberKey())
            prefRepository.setMainHandle(baseContext.getMainHandle())
            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        } else {
            loginBinding.Status.text = getString(R.string.invalidWarning)

        }


    }



}