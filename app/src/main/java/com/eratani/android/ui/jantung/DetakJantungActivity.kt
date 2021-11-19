package com.eratani.android.ui.jantung

import android.os.Bundle
import android.view.MenuItem
import android.widget.SeekBar
import com.eratani.android.R
import com.eratani.android.ui.base.BaseActivity
import com.eratani.android.utils.AppUtil
import com.eratani.android.utils.StatusBarUtil
import kotlinx.android.synthetic.main.activity_jantung.*


class DetakJantungActivity : BaseActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jantung)
        initViews()
        StatusBarUtil.setDarkMode(this)
    }


    private fun initViews() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            AppUtil.insertStatusBarHeight2TopPadding(toolbar)
        }
        toolbar.fitsSystemWindows = false
        toolbar.title = "Detak Jantung"
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        jantungView.setDurationBasedOnBPM(50)
        jantungView.toggle()
        beatsPerMinSeek.max = 150

        beatsPerMinSeek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(p0: SeekBar?, progress: Int, p2: Boolean) {
                var bpm = progress + 50
                jantungView.setDurationBasedOnBPM(bpm)
                tv_bpm.text = "BPM: $bpm"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}