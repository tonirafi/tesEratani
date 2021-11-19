package com.eratani.android.configapp.room

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize


@Entity(tableName = "produk")
@Parcelize
class ProdukModel (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_produk")
    var idProduk: Int? = 0,

    @ColumnInfo(name ="nama_produk")
    var namaProduk: String? = null,

    @ColumnInfo(name ="stok")
    var stok: Int? = null,

    @ColumnInfo(name ="harga")
    var harga: Int? = null,


    @ColumnInfo(name = "image")
    val image: String? = null


) : Parcelable