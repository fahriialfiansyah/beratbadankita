package org.d3ifcool.beratbadankita

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import org.d3ifcool.beratbadankita.databinding.ActivityMainBinding
import org.d3ifcool.beratbadankita.viewmodel.FirebaseUserViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment


    private val viewModel: FirebaseUserViewModel by lazy {
        ViewModelProvider(this)[FirebaseUserViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val content: View = findViewById(android.R.id.content)

        viewModel.authState.observe(this) {
            content.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        return if (it != null) {
//                            Navigation.findNavController(
//                                this@MainActivity, R.id.fragmentContainerView
//                            ).navigate(R.id.onBoardingFragment)
                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            true
                        } else {
                            Navigation.findNavController(
                                this@MainActivity, R.id.fragmentContainerView
                            ).navigate(R.id.loginFragment)

                            content.viewTreeObserver.removeOnPreDrawListener(this)
                            false
                        }
                    }
                }
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.homeFragment -> {
                navHostFragment.navController.popBackStack()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
