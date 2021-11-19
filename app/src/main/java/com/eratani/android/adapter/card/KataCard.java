package com.eratani.android.adapter.card;

import com.eratani.android.adapter.BaseCard;


public class KataCard extends BaseCard {


    public String kata;
    public Boolean selected;


    public KataCard(String kata, Boolean selected) {
        this.kata = kata;
        this.selected = selected;
    }
}
