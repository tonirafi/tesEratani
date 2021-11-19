package com.eratani.android.adapter.card;


import com.eratani.android.adapter.BaseCard;
import com.eratani.android.configapp.room.ProdukModel;
import com.eratani.android.configapp.room.SummeryDataStok;

public class SummeryCard extends BaseCard {
    public SummeryDataStok summeryDataStok;

    public SummeryCard(SummeryDataStok summeryDataStok) {
        this.summeryDataStok = summeryDataStok;
    }


}
