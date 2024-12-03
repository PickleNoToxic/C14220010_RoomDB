package uts.c14220010.c14220010_roomdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import uts.c14220010.c14220010_roomdb.database.daftarBelanja
import uts.c14220010.c14220010_roomdb.database.daftarBelanjaDB
import uts.c14220010.c14220010_roomdb.database.historyBelanjaDB

class MainActivity : AppCompatActivity() {
    private lateinit var DB: daftarBelanjaDB
    private lateinit var DBHistory: historyBelanjaDB
    private lateinit var adapterDaftar: adapterDaftar
    private var arDaftar : MutableList<daftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = daftarBelanjaDB.getDatabase(this)
        DBHistory = historyBelanjaDB.getDatabase(this)

        val _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        _fabAdd.setOnClickListener {
            val intent = Intent(this, TambahDaftar::class.java)
            startActivity(intent)
        }

        val _btnHistory = findViewById<Button>(R.id.btnHistory)
        _btnHistory.setOnClickListener {
            val intent = Intent(this, HistoryBelanjaActivity::class.java)
            startActivity(intent)
        }

        adapterDaftar = adapterDaftar(arDaftar)
        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftar)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        adapterDaftar.setOnItemClickCallback(object : adapterDaftar.OnItemClickCallback {
            override fun delData(dtBelanja: daftarBelanja) {
                //Dispatcher IO untuk memasukkan/menerima Input/Output
                //Coroutine jika atas gagal, bawah tidak dijalankan
                CoroutineScope(Dispatchers.IO).async {
                    DB.fundaftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.fundaftarBelanjaDAO().selectAll()
                    //Dispatcher Main untuk berhubungan dgn UI, menampilkan data
                    withContext(Dispatchers.Main) {
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

            override fun finishData(dtBelanja: daftarBelanja) {
                //Hapus dari daftar belanja
                delData(dtBelanja)

                //Tambahkan ke history belanja
                CoroutineScope(Dispatchers.IO).async {
                    DBHistory.funhistoryBelanjaDAO().insert(dtBelanja)
                }
            }
        })
    }

    override fun onStart() {
        super.onStart()
        //Dispatcher Main untuk berhubungan dgn ui, menampilkan data
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.fundaftarBelanjaDAO().selectAll()
            Log.d("data ROOM", daftarBelanja.toString())

            adapterDaftar.isiData(daftarBelanja)
        }
    }
}