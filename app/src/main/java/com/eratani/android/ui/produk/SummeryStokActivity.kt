package com.eratani.android.ui.produk

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.eratani.android.R
import com.eratani.android.adapter.BaseCard
import com.eratani.android.adapter.CardAdapter
import com.eratani.android.adapter.ItemCardClickListener
import com.eratani.android.adapter.card.ProdukCard
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.utils.AppUtil
import com.eratani.android.utils.StatusBarUtil
import com.eratani.android.utils.widget.UploadImageViewGroup
import kotlinx.android.synthetic.main.layout_card_list_common.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class SummeryStokActivity : BaseActivity(), OnRefreshListener,
    CardAdapter.OnItemClickListener,
    ItemCardClickListener {


    private val produkViewModel: ProdukViewModel by viewModel()
    private val cardAdapter: CardAdapter = CardAdapter(this)
    private var search = ""
    var myMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_card_list_common_with_toolbar)
        initViews()
        setViewModel()
        StatusBarUtil.setDarkMode(this)
    }


    private fun initViews() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AppUtil.insertStatusBarHeight2TopPadding(toolbar)
        }
        cardAdapter.setOnItemClickListener(this)
        toolbar.fitsSystemWindows = false
        toolbar.title = "Rangkuman Stok"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        globalSwapRecyclerView.setAdapter(cardAdapter)
        globalSwapRecyclerView.setRefreshEnabled(false)
        globalSwapRecyclerView.isLoadMoreEnabled = false
        globalSwapRecyclerView.mSwipeLoadMoreFooter
        produkViewModel.preloadCards()
        getDataStok()


        edSearch.addTextChangedListener(
            object : TextWatcher {

                private var timer = Timer()
                private val DELAY: Long = 500 // milliseconds
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun afterTextChanged(s: Editable) {
                    timer.cancel()
                    timer = Timer()
                    timer.schedule(
                        object : TimerTask() {
                            override fun run() {
                                search = edSearch.text.toString()
                                getDataStok()
                            }
                        },
                        DELAY
                    )
                }
            }
        )


    }


    private fun setViewModel() {

        produkViewModel.listBaseCardPre.observe(this, Observer {
            hideAnimationOrLoading()
            updateCards(it, true)
            globalSwapRecyclerView.onCompleteRefresh()
        })
        produkViewModel.listBaseCard.observe(this, Observer {
            hideAnimationOrLoading()
            updateCards(it, true)
            globalSwapRecyclerView.onCompleteRefresh()
        })

        produkViewModel.throwable.observe(this, Observer {
            hideAnimationOrLoading()
            showMessage(it.message)
            globalSwapRecyclerView.onCompleteRefresh()
        })
    }

    fun updateCards(list: List<BaseCard>, reloadAll: Boolean) {


        if (reloadAll) {
            val enable = list.lastOrNull()?.let { it is ProdukCard } ?: false
            globalSwapRecyclerView.isLoadMoreEnabled = false

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


    override fun onRefresh() {
        getDataStok()
    }


    override fun onItemOnclick(position: Int) {

    }


    fun getDataStok() {

            produkViewModel.loadDataStokProduk( search)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menuInflater.inflate(R.menu.menu, menu)
        myMenu = menu
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.search -> {
                edSearch.visibility = View.VISIBLE
                edSearch.isFocusable = true
                item.isVisible = false
                edSearch.requestFocus()
                val imm: InputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(edSearch, InputMethodManager.SHOW_IMPLICIT)
                true
            }
            android.R.id.home -> {
                onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onBackPressed() {
        if (edSearch.isVisible) {
            edSearch.visibility = View.GONE
            val item: MenuItem = myMenu!!.findItem(R.id.search)
            item.isVisible = true
            edSearch.setText("")
            search = ""
            getDataStok()
        } else {
            onComplete()
        }
    }

    override fun viewClick(view: View?, position: Int?) {
        if (view!!.id == R.id.btReload) {
            globalSwapRecyclerView.mSwipeToLoadLayout!!.isRefreshing = true
            getDataStok()
        }
    }


}