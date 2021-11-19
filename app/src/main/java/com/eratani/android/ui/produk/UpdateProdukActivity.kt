package com.eratani.android.ui.produk

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.eratani.android.R
import com.eratani.android.configapp.room.ProdukModel
import com.eratani.android.configapp.router.RouterConstants
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.utils.AppUtil
import com.eratani.android.utils.StatusBarUtil
import com.eratani.android.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_update_produk.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import kotlinx.android.synthetic.main.search_card.btAction
import org.koin.androidx.viewmodel.ext.android.viewModel


class UpdateProdukActivity : BaseActivity(){


    private lateinit var produk: ProdukModel
    private val produkViewModel: ProdukViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_produk)
        initViews()
        StatusBarUtil.setDarkMode(this)
    }


    private fun initViews() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AppUtil.insertStatusBarHeight2TopPadding(toolbar)
        }
        toolbar.fitsSystemWindows = false
        toolbar.title = "Update Produk"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val uri = parseIntent(intent)
        uri?.getQueryParameter(RouterConstants.Params.PRE_LOAD)?.let {
            produk = AppUtil.getGsonInstance().fromJson<ProdukModel>(it, ProdukModel::class.java)
        }

        edNamaProduk.setText(produk.namaProduk)
        edJumlahStok.setText(produk.stok.toString())
        edHarga.setText(produk.harga.toString())

        btAction.setOnClickListener {
            showLoading()
            if(validaiData()){
                produk.namaProduk=edNamaProduk.text.toString()
                produk.stok=edJumlahStok.text.toString().toInt()
                produk.harga=edHarga.text.toString().toInt()
                produkViewModel.updateProduk(produk)
            }else{
               ToastUtil.show("Semua data harus di isi")
            }

        }

        setViewModel()


    }

    fun validaiData():Boolean{
        return !(edNamaProduk.text.toString()=="" || edJumlahStok.text.toString()==""|| edHarga.text.toString()=="")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setViewModel() {


        produkViewModel.respone.observe(this, Observer {

            if(it==200){
                hideAnimationOrLoading()
                ToastUtil.show("Data terupdate")
                finish()
            }
        })

        produkViewModel.throwable.observe(this, Observer {
            hideAnimationOrLoading()
            showMessage(it.message)
        })
    }

}