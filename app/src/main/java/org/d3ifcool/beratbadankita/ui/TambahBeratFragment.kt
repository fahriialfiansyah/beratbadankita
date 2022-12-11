package org.d3ifcool.beratbadankita.ui

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.common.reflect.Reflection.getPackageName
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.data.User
import org.d3ifcool.beratbadankita.databinding.FragmentCekIdealBinding
import org.d3ifcool.beratbadankita.databinding.FragmentTambahBeratBinding
import java.text.SimpleDateFormat
import java.util.*

class TambahBeratFragment : BottomSheetDialogFragment() {

    private var year = 0
    private var month = 0
    private var day = 0

    private var _binding: FragmentTambahBeratBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private lateinit var calendar: Calendar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_tambah_berat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentTambahBeratBinding.bind(view)
        binding.apply {
            btnPilihTanggal.setOnClickListener {
                calendar = Calendar.getInstance()
                year = calendar.get(Calendar.YEAR)
                month = calendar.get(Calendar.MONTH)
                day = calendar.get(Calendar.DAY_OF_MONTH)
                val dialog = DatePickerDialog(
                    requireContext(), { _, year, month, day_of_month ->
                        calendar[Calendar.YEAR] = year
                        calendar[Calendar.MONTH] = month
                        calendar[Calendar.DAY_OF_MONTH] = day_of_month
                        val myFormat = "dd MMM yyyy"
                        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                        btnPilihTanggal.text = sdf.format(calendar.time)

                    }, calendar[Calendar.YEAR], calendar[Calendar.MONTH],
                    calendar[Calendar.DAY_OF_MONTH]
                )
                dialog.datePicker.minDate = calendar.timeInMillis
                calendar.add(Calendar.YEAR, 0)
                dialog.datePicker.maxDate = calendar.timeInMillis
                dialog.show()
            }
        }
        save_validasi()
//                val datePickerFragment = DatePickerFragment()
//                val supportFragmentManager = requireActivity().supportFragmentManager
//
//                supportFragmentManager.setFragmentResultListener(
//                    "REQUEST_KEY",
//                    viewLifecycleOwner
//                ) { resultKey, bundle ->
//                    if (resultKey == "REQUEST_KEY") {
//                        val date = bundle.getString("SELECTED_DATE")
//                        btnPilihTanggal.text = date
//                    }
//                }
//
//                datePickerFragment.show(supportFragmentManager, "DatePickerFragment")
    }

    private fun save_validasi() {

        binding.btnSimpan.setOnClickListener {
            var beratBadan = binding.inputBeratBadan.text.toString()
            var catatan = binding.inputCatatan.text.toString()

            if (beratBadan.isEmpty()) {
                binding.tilBeratBadan.error = "Masukkan berat badan kamu saat ini"
            } else {
                binding.tilBeratBadan.error = null
            }
            if (beratBadan.isNotEmpty()) {
                database = FirebaseDatabase.getInstance().getReference("History")

                beratBadan = String.format("%.1f", beratBadan.toFloat())

                val beratId = database.push().key!!
                val userId = FirebaseAuth.getInstance().currentUser?.uid

                val time: Long = System.currentTimeMillis()

                val riwayat = History(userId, beratId, beratBadan, catatan, time)

                if (userId != null) {
                    database.child(beratId).setValue(riwayat).addOnSuccessListener {
                        binding.inputBeratBadan.text?.clear()
                        binding.inputCatatan.text?.clear()
                        dismiss()
                    }
                }

//                Toast.makeText(context, "Data berat badan berhasil masuk", Toast.LENGTH_SHORT).show()

//                findNavController().navigate(R.id.action_onBoardingFragment_to_bottomNavFragment)
            }
        }
    }


}