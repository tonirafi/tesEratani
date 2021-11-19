package com.eratani.android.adapter;

import android.text.TextUtils;


import com.eratani.android.adapter.card.ProdukCard;
import com.eratani.android.adapter.card.StokCard;
import com.eratani.android.adapter.card.SummeryCard;

import java.util.List;


public class CardMapInitializer implements ICardMapInitializer {

    private static final String CARD_DIR = "com.eratani.android.adapter.card.";
    private static final String PROVIDER_DIR = "com.eratani.android.adapter.providers.";

    @Override
    public void initRouterTable(List<String> cardNameList, List<String> providerNameList) {
        addCardProviderPair(cardNameList, providerNameList, "MoreCard", "MoreCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "EmptyErrorItemCard", "EmptyErrorItemCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "KataCard", "KataCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "SearchCard", "SearchCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "PointCard", "PointCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "ProdukCard", "ProdukCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "StokCard", "StokCardProvider");
        addCardProviderPair(cardNameList, providerNameList, "SummeryCard", "SummeryCardProvider");


    }


    /**
     * @param cardNameList
     * @param providerNameList
     * @param cardName
     * @param providerName
     */
    public void addCardProviderPair(List<String> cardNameList, List<String> providerNameList, String cardName, String providerName) {
        if (TextUtils.isEmpty(cardName) || TextUtils.isEmpty(providerName))
            return;

        if (cardName.contains("."))
            cardNameList.add(cardName);
        else
            cardNameList.add(CARD_DIR + cardName);


        if (providerName.contains("."))
            providerNameList.add(providerName);
        else
            providerNameList.add(PROVIDER_DIR + providerName);

    }
}
