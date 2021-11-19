package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.PointCard;

import butterknife.BindView;

@CardMap(PointCard.class)
public class PointCardProvider extends ItemViewProvider<PointCard, PointCardProvider.ViewHolder> {


    public PointCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.point_card, parent, false),mOnItemClickListener);
    }


    public static class ViewHolder extends CommonVh<PointCard> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }

        @BindView(R.id.text)
        TextView text;


        @Override
        public void bind(PointCard card) {
            super.bind(card);
            if (card.kata != null) {
                text.setText(card.kata);
            }

        }
    }
}
