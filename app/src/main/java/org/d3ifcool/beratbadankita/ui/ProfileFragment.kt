package org.d3ifcool.beratbadankita.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import org.d3ifcool.beratbadankita.MainActivity
import org.d3ifcool.beratbadankita.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val user = FirebaseAuth.getInstance()

        if (user.currentUser != null) {
            user.currentUser?.let {

                binding.tvEmail.text = it.email

            }
        }

        binding.logout.setOnClickListener {
            user.signOut()
            startActivity(
                Intent(
                    context,
                    MainActivity::class.java
                )
            )

        }

        binding.logout.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.getIdToken(false)
                ?.addOnSuccessListener { result ->
                    when (result.signInProvider) {
                        "google.com" -> {
                            context?.let { it1 ->
                                GoogleSignIn.getClient(
                                    it1,
                                    GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                        .build()
                                ).signOut()
                            }
                            signOutFromApp()
                        }
                        else -> {
                            signOutFromApp()
                        }
                    }
                }
        }


    }

    fun signOutFromApp() {
        FirebaseAuth.getInstance().signOut()
        startActivity(
            Intent(
                context,
                MainActivity::class.java
            )) //starts login view
        requireActivity().fragmentManager.popBackStack() //finish settigs view
    }
//    private fun closefragment() {
//        activity!!.fragmentManager.beginTransaction().remove(this).commit()
//    }

}