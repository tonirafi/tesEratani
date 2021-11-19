package com.eratani.android.adapter.card;


import com.eratani.android.adapter.BaseCard;
import com.eratani.android.configapp.room.ProdukModel;

public class StokCard extends BaseCard {
    public ProdukModel produkModel;

    public StokCard(ProdukModel produkModel) {
        this.produkModel = produkModel;
    }


}
