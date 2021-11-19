package com.eratani.android.ui.carikata

import android.os.Bundle
import android.view.MenuItem
import com.eratani.android.R
import com.eratani.android.adapter.BaseCard
import com.eratani.android.adapter.CardAdapter
import com.eratani.android.adapter.EditTextClickListener
import com.eratani.android.adapter.card.KataCard
import com.eratani.android.adapter.card.ProdukCard
import com.eratani.android.adapter.card.SearchCard
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.utils.AppUtil
import com.eratani.android.utils.StatusBarUtil
import com.eratani.android.utils.ToastUtil
import kotlinx.android.synthetic.main.layout_card_list_common.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import java.util.*


class PencarianKataActivity : BaseActivity(),
    EditTextClickListener,
    CardAdapter.OnItemClickListener {
    private val allData = arrayOf("Pergi", "Pulang", "kembali", "Datang", "jalan", "Makan")
    private val cardAdapter: CardAdapter = CardAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_card_list_common_with_toolbar)
        initViews()
        StatusBarUtil.setDarkMode(this)
    }


    private fun initViews() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AppUtil.insertStatusBarHeight2TopPadding(toolbar)
        }
        cardAdapter.setOnItemClickListener(this)
        toolbar.fitsSystemWindows = false
        toolbar.title = "Pencarian Kata"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        globalSwapRecyclerView.setAdapter(cardAdapter)
        globalSwapRecyclerView.setRefreshEnabled(false)
        globalSwapRecyclerView.isLoadMoreEnabled = false
        globalSwapRecyclerView.mSwipeLoadMoreFooter
        setData()
    }

    fun cariKata(karakter: String){
        var baseCards = ArrayList<BaseCard>()
        var x = 0
        var adaKata = false
        for (i in x until allData.size) {
            if (karakter == allData[i]) {
                baseCards.add(KataCard(allData[i], true))
                adaKata=true
            }else{
                baseCards.add(KataCard(allData[i], false))
            }
        }
        if(!adaKata){
            ToastUtil.show("Yaa kata yang di cari tidak ada.")
        }
        baseCards.add(SearchCard())
        updateCards(baseCards, true)

    }


    fun setData(){
        var baseCards = ArrayList<BaseCard>()

        for (data in allData){
            baseCards.add(KataCard(data, false))

        }
        baseCards.add(SearchCard())
        updateCards(baseCards, true)
    }


    fun updateCards(list: List<BaseCard>, reloadAll: Boolean) {

        if (reloadAll) {
            val enable = list.lastOrNull()?.let { it is ProdukCard } ?: false
            globalSwapRecyclerView.isLoadMoreEnabled = enable

            cardAdapter.list.clear()
            cardAdapter.addAll(list)
            return
        }

        if (list.isEmpty()) {
            globalSwapRecyclerView.onCompleteRefresh()
            globalSwapRecyclerView.isLoadMoreEnabled = false
            return
        }

        val insertIndex = cardAdapter.list.size
        cardAdapter.list.addAll(insertIndex, list)
        cardAdapter.notifyItemRangeInserted(insertIndex, list.size)
    }

    override fun search(value: String?) {
        cariKata(value!!)
    }

    override fun onItemOnclick(position: Int) {

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}