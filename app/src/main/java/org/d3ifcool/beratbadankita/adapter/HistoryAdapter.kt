package org.d3ifcool.beratbadankita.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.d3ifcool.beratbadankita.R
import org.d3ifcool.beratbadankita.data.History
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter (mContext: Context, mHistory: List<History>) : RecyclerView.Adapter<HistoryAdapter.ViewHolder?>() {

    private val mContext: Context
    private val mHistory: List<History>

    init {
        this.mContext = mContext
        this.mHistory = mHistory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(mContext).inflate(R.layout.item_riwayat_berat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val date = SimpleDateFormat("dd MMMM yyyy", Locale.ENGLISH)
        val history: History = mHistory[position]
        holder.dateText.text = history.date.let { date.format(it) }
        holder.noteText.text = history.catatan
        holder.weightText.text = history.saatIni.toString() + " kg"
//        holder.differenceText.text = history.tujuan.toString()
    }

    override fun getItemCount(): Int {
        return mHistory.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var dateText: TextView
        var noteText: TextView
        var weightText: TextView
//        var differenceText: TextView

        init {
            dateText = itemView.findViewById(R.id.tvDate)
            noteText = itemView.findViewById(R.id.tvNote)
            weightText = itemView.findViewById(R.id.tvWeight)
//            differenceText = itemView.findViewById(R.id.tvDifference)
        }
    }
}