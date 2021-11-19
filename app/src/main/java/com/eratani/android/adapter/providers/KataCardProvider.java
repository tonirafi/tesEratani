package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.KataCard;

import butterknife.BindView;

@CardMap(KataCard.class)
public class KataCardProvider extends ItemViewProvider<KataCard, KataCardProvider.ViewHolder> {


    public KataCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.kata_card, parent, false));
    }


    public static class ViewHolder extends CommonVh<KataCard> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }

        @BindView(R.id.text)
        TextView text;

        @BindView(R.id.imgSelected)
        ImageView imgSelected;

        @Override
        public void bind(KataCard card) {
            super.bind(card);
            if (card.kata != null) {
                text.setText(card.kata);
            }

            if(card.selected){
                imgSelected.setVisibility(View.VISIBLE);
            }else {
                imgSelected.setVisibility(View.INVISIBLE);

            }
//            itemView.setBackgroundResource(card.backgroundRes);
        }
    }
}
