package com.eratani.android.adapter.card;


import com.eratani.android.adapter.BaseCard;

public class EmptyErrorItemCard extends BaseCard {
    public String title;
    public Boolean showButton;

    public EmptyErrorItemCard(String title, Boolean showButton) {
        this.title = title;
        this.showButton = showButton;
    }


}
