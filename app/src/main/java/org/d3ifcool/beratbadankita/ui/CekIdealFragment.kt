package org.d3ifcool.beratbadankita.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.databinding.FragmentCekIdealBinding
import org.d3ifcool.beratbadankita.databinding.FragmentHomeBinding

class CekIdealFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCekIdealBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCekIdealBinding.inflate(layoutInflater, container, false)
        return binding.root
//        berat / tinggi / tinggi x 10000
//        MINIM
//        --------
//        berat = 18.5 * tinggi * tinggi / 10000
//
//        MAX
//        --------
//        berat = 24.9 * tinggi * tinggi / 10000
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        binding.btnCek.setOnClickListener {
            val tinggi = binding.inputTinggi.text.toString()


            if (tinggi.isEmpty()) {
                binding.tinggiBadanSaatIni.error = "Masukkan tinggi badan kamu saat ini"
            } else if (tinggi.toFloat() < 5.0) {
                binding.tvRekomendasiBerat.text = null
                binding.tinggiBadanSaatIni.error = "Tinggi badan kamu harus lebih besar dari 4"
            }
            else if (tinggi.isNotEmpty()) {
                binding.tinggiBadanSaatIni.isErrorEnabled = false

                var berat_minim = (18.5 * tinggi.toFloat() * tinggi.toFloat() / 10000).toString()
                var berat_max = (24.9 * tinggi.toFloat() * tinggi.toFloat() / 10000).toString()

                berat_minim = String.format("%.1f", berat_minim.toFloat())
                berat_max = String.format("%.1f", berat_max.toFloat())

                binding.tvRekomendasiBerat.visibility = View.VISIBLE
                binding.tvRekomendasiBerat.text = "Rekomendasi berat badan kamu: $berat_minim - $berat_max kg"
            }

        }
    }
}