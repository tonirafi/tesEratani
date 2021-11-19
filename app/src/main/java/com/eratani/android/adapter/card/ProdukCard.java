package com.eratani.android.adapter.card;


import com.eratani.android.adapter.BaseCard;
import com.eratani.android.configapp.room.ProdukModel;

public class ProdukCard extends BaseCard {
    public ProdukModel produkModel;

    public ProdukCard(ProdukModel produkModel) {
        this.produkModel = produkModel;
    }


}
