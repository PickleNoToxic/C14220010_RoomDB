package uts.c14220010.c14220010_roomdb.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Data class daftarBelanja adalah tabel di database
@Entity
data class daftarBelanja(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int = 0,

    @ColumnInfo(name = "tanggal")
    var tanggal: String? = null,

    @ColumnInfo(name = "item")
    var item: String? = null,

    @ColumnInfo(name = "jumlah")
    var jumlah: String? = null,

    @ColumnInfo(name = "status")
    var status: Int = 0
)
