package com.eratani.android.adapter.card;

import com.eratani.android.adapter.BaseCard;

import java.util.Map;


public abstract class EditCard extends BaseCard {

    public String _key = this.getClass().getSimpleName();
    public boolean skipCollect = false;

    public Map<String, Object> collect(Map<String, Object> map) {
        if (!skipCollect) {
            map.put(_key, getValue());
        }
        return map;
    }

    public abstract Object getValue();
}
