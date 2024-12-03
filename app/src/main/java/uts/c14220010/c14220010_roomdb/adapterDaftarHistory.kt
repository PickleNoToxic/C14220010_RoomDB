package uts.c14220010.c14220010_roomdb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import uts.c14220010.c14220010_roomdb.database.daftarBelanja

class adapterDaftarHistory(private val daftarBelanja: MutableList<daftarBelanja>) : RecyclerView.Adapter<adapterDaftarHistory.ListViewHolder>() {
    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var _tvTanggal = itemView.findViewById<TextView>(R.id.tvTanggal)
        var _tvItemBarang = itemView.findViewById<TextView>(R.id.tvItem)
        var _tvJumlahBarang = itemView.findViewById<TextView>(R.id.tvJumlah)
        var _btnEdit = itemView.findViewById<FloatingActionButton>(R.id.btnEdit)
        var _btnDelete = itemView.findViewById<FloatingActionButton>(R.id.btnHapus)
        var _btnFinish = itemView.findViewById<FloatingActionButton>(R.id.btnFinish)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_history, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var daftar = daftarBelanja[position]
        holder._tvTanggal.setText(daftar.tanggal)
        holder._tvItemBarang.setText(daftar.item)
        holder._tvJumlahBarang.setText(daftar.jumlah)
    }

    fun isiData(daftar: List<daftarBelanja>){
        daftarBelanja.clear()
        daftarBelanja.addAll(daftar)
        notifyDataSetChanged()
    }
}