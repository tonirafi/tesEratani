package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.ItemCardClickListener;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.EmptyErrorItemCard;

import butterknife.BindView;

@CardMap(EmptyErrorItemCard.class)
public class EmptyErrorItemCardProvider extends ItemViewProvider<EmptyErrorItemCard, EmptyErrorItemCardProvider.ViewHolder> {


    public EmptyErrorItemCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_empty_error, parent, false), mOnItemClickListener);
    }


    public static class ViewHolder extends CommonVh<EmptyErrorItemCard> {
        @BindView(R.id.tvInfo)
        TextView tvInfo;

        @BindView(R.id.btReload)
        Button btReload;


        public ViewHolder(View itemView) {
            this(itemView, null);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }


        @Override
        public void bind(EmptyErrorItemCard card) {
            super.bind(card);
            tvInfo.setText(card.title);

            if (card.showButton) {
                btReload.setVisibility(View.VISIBLE);

            } else {
                btReload.setVisibility(View.GONE);

            }

            btReload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((ItemCardClickListener) onItemClickListener).viewClick(v, getAdapterPosition());

                }
            });

        }


    }
}
