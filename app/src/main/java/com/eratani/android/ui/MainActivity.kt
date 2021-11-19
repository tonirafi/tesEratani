package com.eratani.android.ui

import android.content.Intent
import android.os.Bundle
import com.eratani.android.R
import com.eratani.android.adapter.BaseCard
import com.eratani.android.adapter.CardAdapter
import com.eratani.android.adapter.card.ProdukCard
import com.eratani.android.adapter.card.PointCard
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.ui.carikata.PencarianKataActivity
import com.eratani.android.ui.jantung.DetakJantungActivity
import com.eratani.android.ui.produk.ProdukActivity
import com.eratani.android.utils.AppUtil
import com.eratani.android.utils.StatusBarUtil
import kotlinx.android.synthetic.main.layout_card_list_common.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*


class MainActivity : BaseActivity(),
    CardAdapter.OnItemClickListener {



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
        toolbar.title = "Menu Pengerjaan Tes"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        globalSwapRecyclerView.setAdapter(cardAdapter)
        setMenu()
        globalSwapRecyclerView.setRefreshEnabled(false)
        globalSwapRecyclerView.isLoadMoreEnabled = false
        globalSwapRecyclerView.mSwipeLoadMoreFooter

    }

    fun setMenu(){
        var baseCards = ArrayList<BaseCard>()
        baseCards.add(PointCard("1. Pencarian Kata"))
        baseCards.add(PointCard("2. Pemprosesan Data"))
        baseCards.add(PointCard("3. Animasi Jantung"))
        updateCards(baseCards,true)
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

    override fun onItemOnclick(position: Int) {
        var intent=Intent()
        when (position) {
            0 -> {
                intent=Intent(this, PencarianKataActivity::class.java)
            }
            1 -> {
                intent=Intent(this,ProdukActivity::class.java)
            }
            2 -> {
                intent=Intent(this, DetakJantungActivity::class.java)
            }
        }
        startActivity(intent)
    }


}