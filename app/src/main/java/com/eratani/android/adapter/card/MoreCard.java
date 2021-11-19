package com.eratani.android.adapter.card;


import com.eratani.android.adapter.BaseCard;

public class MoreCard extends BaseCard {
    public String groupId;
    public String shortLink;

    public MoreCard(String shortLink) {
        this.shortLink = shortLink;
    }
}
