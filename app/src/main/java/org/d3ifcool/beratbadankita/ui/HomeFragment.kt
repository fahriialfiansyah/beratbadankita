package org.d3ifcool.beratbadankita.ui

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.databinding.FragmentHomeBinding
import org.d3ifcool.beratbadankita.db.UserDb
import org.d3ifcool.beratbadankita.ui.onboarding.OnBoardingViewModel
import org.d3ifcool.beratbadankita.ui.onboarding.OnBoardingViewModelFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var database : DatabaseReference


    private val viewModel: OnBoardingViewModel by lazy {
        val db = UserDb.getInstance(requireContext())
        val factory = OnBoardingViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[OnBoardingViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setHasOptionsMenu(true)
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.ibTambahBerat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavFragment_to_tambahBeratFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        database = FirebaseDatabase.getInstance().getReference("User")

//        val beratId = database.push().key!!
        val userId = FirebaseAuth.getInstance().currentUser?.uid
//        Log.i("User ID", "$userId berhasil login")

        if (userId != null) {
            Log.i("User ID", "$userId berhasil login")
            readData(userId)
        }



//        val nama = FirebaseAuth.getInstance().currentUser?.displayName
//        Log.i("LOGIN", "$nama berhasil login")

    }

    fun readData(userId: String) {

        database = FirebaseDatabase.getInstance().getReference("User")

        database.child(userId).get().addOnSuccessListener {
            if (it.exists()) {

                val saatIni = it.child("saatIni").value
                val tujuan = it.child("tujuan").value
                binding.tvStart.text = saatIni.toString()
                binding.tvCurrent.text = saatIni.toString()
                binding.tvGoal.text = tujuan.toString()
                Toast.makeText(context, "Data berhasil diambil", Toast.LENGTH_SHORT).show()

            } else {

                Toast.makeText(context, "Kamu belum menambahkan data berat badan", Toast.LENGTH_SHORT).show()

            }
        }
    }

    private fun setFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment, fragment)
            addToBackStack(null)
            commit()
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.add_menu, menu)
//    }
//
////    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
////        inflater.inflate(R.menu.menu_sample, menu)
////        super.onCreateOptionsMenu(menu!!, inflater)
////    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.tambahBeratFragment -> {
////                findNavController().navigate(HomeFragmentDirections.actionNavigateAddWeight(null))
//                findNavController().navigate(R.id.action_splashScreen_to_loginFragment)
//
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }


//        var counter = 0
//
//        viewModel.getOnBoardingData().observe(viewLifecycleOwner) {
//            if (it == null) return@observe
//
//            Log.d("Loooh", it.beratSaatIni.toString())
//            with(binding) {
//                tvCurrent.text = it.beratSaatIni.toString()
//                tvGoal.text = it.beratTujuan.toString()
//
//                if (counter == 0) {
//                    tvStart.text = it.beratSaatIni.toString()
//                    counter++
//
//                }
//            }
//        }
}