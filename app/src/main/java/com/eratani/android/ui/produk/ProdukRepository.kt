package com.eratani.android.ui.produk

import com.eratani.android.configapp.http.bean.BaseBean
import com.eratani.android.configapp.room.ProdukDao
import com.eratani.android.configapp.room.ProdukModel
import com.eratani.android.configapp.room.SummeryDataStok
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent

class ProdukRepository( val produkDao: ProdukDao) : KoinComponent {

    fun loadDataProdak(search:String): Observable<List<ProdukModel>>? {
        return  produkDao?.getAllProduk(search).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }

    fun loadDataSummery(): Observable<SummeryDataStok>? {
        return  produkDao?.getSummeryStok().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


 fun updateProduk(addProduk: ProdukModel): Completable {
        return  produkDao?.updateProduk(addProduk).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    }


}