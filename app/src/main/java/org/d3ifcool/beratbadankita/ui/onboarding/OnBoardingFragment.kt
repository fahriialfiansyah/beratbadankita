package org.d3ifcool.beratbadankita.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.data.User
import org.d3ifcool.beratbadankita.databinding.FragmentOnBoardingBinding
import org.d3ifcool.beratbadankita.viewmodel.FirebaseUserViewModel


class OnBoardingFragment : Fragment() {
    private lateinit var binding: FragmentOnBoardingBinding
    private lateinit var database: DatabaseReference
    private lateinit var databaseHistory: DatabaseReference


//    private val viewModel: OnBoardingViewModel by lazy {
//        val db = OnBoardingDb.getInstance(requireContext())
//        val factory = OnBoardingViewModelFactory(db.dao)
//        ViewModelProvider(this, factory)[OnBoardingViewModel::class.java]
//    }
    private val viewModel: FirebaseUserViewModel by lazy {
        ViewModelProvider(this)[FirebaseUserViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOnBoardingBinding.inflate(layoutInflater, container, false)
        binding.tvKlikDisini.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_cekIdealFragment)
        }
//        val mAuth = FirebaseAuth.getInstance()

//        val ref = mAuth.currentUser?.uid
//        val databaseRef = FirebaseDatabase.getInstance().getReference().child("User")
        val rootRef = FirebaseDatabase.getInstance().reference
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val uidRef = rootRef.child("User").child(uid)
        uidRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val snapshot = task.result
                val userId = snapshot.child("userId").getValue(String::class.java)
                Log.d("TAG", "userId: $userId")
                if (uid == userId) {
                    findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
                } else if (userId == null){
                    save_validasi()
                }
            } else {
                Log.d("TAG", task.exception!!.message!!) //Don't ignore potential errors!
            }
        }

//        databaseRef.orderByChild("userId").equalTo("Tvp3BJftGpU0T3RvIL9wFAB4NbI3").addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                if (snapshot.exists()) {
//                    Log.i("OUR INFO", snapshot.value.toString())
////                    findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
//                } else {
//                    save_validasi()
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//
//            }
//        })

//        if (mAuth.currentUser  != null) {
//            findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
//        } else {
//            save_validasi()
//        }

        return binding.root
    }

    private fun save_validasi() {

        binding.mulai.setOnClickListener {
            var saatIni = binding.inputCurrent.text.toString()
            var tujuan = binding.inputGoal.text.toString()

            if (saatIni.isEmpty()) {
                binding.beratBadanSaatIni.error = "Masukkan berat badan kamu saat ini"
            } else {
                binding.beratBadanSaatIni.error = null
            }
            if (tujuan.isEmpty()) {
                binding.beratBadanTujuan.error = "Masukkan berat badan tujuan kamu"
            } else {
                binding.beratBadanTujuan.error = null
            }
            if (tujuan.isNotEmpty() && saatIni == tujuan) {
                binding.beratBadanTujuan.error = "Berat badan tujuan kamu harus berbeda dengan berat badan saat ini"
            } else if (saatIni.isNotEmpty() && tujuan.isNotEmpty()) {
//                viewModel.insertOnBoardingData(saatIni.toFloat(), tujuan.toFloat())

                database = FirebaseDatabase.getInstance().getReference("User")
                databaseHistory = FirebaseDatabase.getInstance().getReference("History")

                saatIni = String.format("%.1f", saatIni.toFloat())
                tujuan = String.format("%.1f", tujuan.toFloat())

                val beratId = database.push().key!!
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                val time : Long = System.currentTimeMillis()

                val onBoarding = User(userId, beratId, saatIni, tujuan, time)
                val riwayat = History(userId, beratId, saatIni, catatan = null, time)

                if (userId != null) {
                    database.child(userId).setValue(onBoarding).addOnSuccessListener {
                        binding.inputCurrent.text?.clear()
                        binding.inputGoal.text?.clear()
                    }
                    databaseHistory.child(userId).setValue(riwayat).addOnSuccessListener {
                        binding.inputCurrent.text?.clear()
                        binding.inputGoal.text?.clear()
                    }
                }

                Toast.makeText(context, "Data berhasil masuk", Toast.LENGTH_SHORT).show()

                findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
            }
        }
    }

    fun onBackPressed() {
        Toast.makeText(context, "Disabled Back Press", Toast.LENGTH_SHORT).show()
    }
}