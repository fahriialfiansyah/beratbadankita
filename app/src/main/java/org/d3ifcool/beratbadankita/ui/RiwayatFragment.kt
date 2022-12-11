package org.d3ifcool.beratbadankita.ui

//import org.d3ifcool.beratbadankita.MainViewModel
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.adapter.HistoryAdapter
import org.d3ifcool.beratbadankita.data.History
import org.d3ifcool.beratbadankita.databinding.FragmentRiwayatBinding


class RiwayatFragment : Fragment() {

    private lateinit var binding: FragmentRiwayatBinding
    private var historyAdapter: HistoryAdapter? = null
    private var mHistory: List<History>? = null
    private var recyclerView: RecyclerView? = null

//    private val viewModel: MainViewModel by lazy {
//        ViewModelProvider(this)[MainViewModel::class.java]
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRiwayatBinding.inflate(layoutInflater, container, false)

        binding.ibTambahBerat.setOnClickListener {
            findNavController().navigate(R.id.action_bottomNavFragment_to_tambahBeratFragment)
        }

        recyclerView = binding.rvWeightHistory
        recyclerView!!.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }
        mHistory = ArrayList()
        retrieveAllHistory()

        return binding.root
    }

    private fun retrieveAllHistory(){
        val mAuth = FirebaseAuth.getInstance()
        val refHistory = FirebaseDatabase.getInstance().reference.child("History").orderByChild("userId").equalTo(mAuth.currentUser!!.uid)
        refHistory.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (mHistory as ArrayList<History>).clear()
                for (data in snapshot.children){
                    val history: History? = data.getValue(History::class.java)
                    if (history != null) {
                        (mHistory as ArrayList<History>).add(history)
                    }
                }
                historyAdapter = HistoryAdapter(context!!, mHistory!!)
                recyclerView!!.adapter = historyAdapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}