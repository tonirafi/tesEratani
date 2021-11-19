package com.eratani.android.ui.produk

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.eratani.android.adapter.BaseCard
import com.eratani.android.adapter.card.ProdukCard
import com.eratani.android.adapter.card.StokCard
import com.eratani.android.adapter.card.SummeryCard
import com.eratani.android.configapp.http.bean.BaseBean
import com.eratani.android.configapp.http.bean.BaseResponse
import com.eratani.android.configapp.room.ProdukModel
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class ProdukViewModel( val produkRepository: ProdukRepository) : ViewModel() {



    val listBaseCardPre by lazy {
        MutableLiveData<ArrayList<BaseCard>>()
    }

    val listBaseCard by lazy {
        MutableLiveData<ArrayList<BaseCard>>()
    }

    val respone by lazy {
        MutableLiveData<Int>()
    }

    val throwable by lazy {
        MutableLiveData<Throwable>()
    }



    private fun getObservable(search: String): Observable<ArrayList<BaseCard>> = Observable.zip(
        produkRepository?.loadDataSummery(),
        produkRepository?.loadDataProdak("%$search%"),
        BiFunction(){ t1, t2 ->

            var baseCards = ArrayList<BaseCard>()

            baseCards.add(SummeryCard(t1))

            for (data in t2!!) {
                baseCards.add( StokCard(data ))
            }

            baseCards
        })

    @SuppressLint("CheckResult")
    fun loadDataProduk(search: String) {

        produkRepository.loadDataProdak("%$search%")
            ?.map {

                var baseCards = ArrayList<BaseCard>()

                    for (data in it!!) {
                        baseCards.add( ProdukCard(data ))
                    }

                baseCards
            }
            ?.subscribe({
                listBaseCard.postValue(it)

            }, {
                throwable.postValue(it)
            })
    }



    @SuppressLint("CheckResult")
    fun loadDataStokProduk(search: String) {
        getObservable(search)
            ?.subscribe({
                listBaseCard.postValue(it)

            }, {
                throwable.postValue(it)
            })
    }


    @SuppressLint("CheckResult")
    fun updateProduk(produkModel: ProdukModel) {

        produkRepository.updateProduk(produkModel)
            ?.subscribe({
                respone.postValue(200)

            }, {
                throwable.postValue(it)
            })
    }


    fun preloadCards() {
        val baseCards = ArrayList<BaseCard>()

        for (i in 0..7) {
            baseCards.add(ProdukCard(null).apply {
                loading = true
            })
        }

        listBaseCardPre.postValue(baseCards)

    }


}