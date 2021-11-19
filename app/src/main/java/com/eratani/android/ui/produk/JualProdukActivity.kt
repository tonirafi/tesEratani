package com.eratani.android.ui.produk

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import com.eratani.android.R
import com.eratani.android.configapp.room.ProdukModel
import com.eratani.android.configapp.router.RouterConstants
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.utils.*
import kotlinx.android.synthetic.main.activity_jual_produk.*
import kotlinx.android.synthetic.main.layout_common_toolbar.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class JualProdukActivity : BaseActivity(){


    private lateinit var produk: ProdukModel
    private val produkViewModel: ProdukViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jual_produk)
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
        PicassoUtil.load(produk.image).centerCrop().fit().into(ic_image)
        tvNameProduk.setText(produk.namaProduk)
        tvHarga.setText(StringUtil.formatCurrency("Rp ", produk.harga.toString()))
        tvStok.setText("Stok : " + produk.stok)

        btAction.setOnClickListener {
            if(validaiData()){
                var jumlahPembelian=edJumlahStok.text.toString().toInt()

                if(jumlahPembelian <= produk.stok!!){
                    showLoading()
                    produk.stok= produk.stok!! - edJumlahStok.text.toString().toInt()
                    produkViewModel.updateProduk(produk)
                }else{
                    ToastUtil.show("Pastikan stok sesuai dengan pembelian")
                }

            }else{
               ToastUtil.show("Jumlah pembelian harus di isi dan tidak boleh 0")
            }

        }

        setViewModel()

    }

    fun validaiData():Boolean{
        return !(edJumlahStok.text.toString()=="" || edJumlahStok.text.toString()=="0" )
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
                ToastUtil.show("Penjualan Berhasil")
                finish()
            }
        })

        produkViewModel.throwable.observe(this, Observer {
            hideAnimationOrLoading()
            showMessage(it.message)
        })
    }



}