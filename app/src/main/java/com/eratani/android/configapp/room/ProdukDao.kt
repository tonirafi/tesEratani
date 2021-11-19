package com.eratani.android.configapp.room

import androidx.room.*
import com.eratani.android.configapp.http.bean.BaseBean
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface ProdukDao {

    @Query("select * from produk where nama_produk like :search")
    fun getAllProduk(search:String): Observable<List<ProdukModel>>

    @Query("select SUM(stok) as totalStok, SUM(stok*harga) as totalUang,COUNT(id_produk) as totalBarang from produk")
    fun getSummeryStok(): Observable<SummeryDataStok>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addProduk(addProduk: ProdukModel): Completable

    @Update
    fun updateProduk(produk: ProdukModel):Completable

    @Delete
    fun deleteProduk(produk: ProdukModel): Completable
}