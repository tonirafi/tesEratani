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
import com.eratani.android.adapter.card.SummeryCard;
import com.eratani.android.configapp.room.SummeryDataStok;
import com.eratani.android.utils.StringUtil;

import butterknife.BindView;

@CardMap(SummeryCard.class)
public class SummeryCardProvider extends ItemViewProvider<SummeryCard, SummeryCardProvider.ViewHolder> {


    public SummeryCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_summery, parent, false), mOnItemClickListener);
    }


    public static class ViewHolder extends CommonVh<SummeryCard> {
        @BindView(R.id.tvTotalBarang)
        TextView tvTotalBarang;

        @BindView(R.id.tvTotalUang)
        TextView tvTotalUang;

        @BindView(R.id.tvTotalStok)
        TextView tvTotalStok;

        public ViewHolder(View itemView) {
            this(itemView, null);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }


        @Override
        public void bind(SummeryCard card) {
            super.bind(card);

            SummeryDataStok data = card.summeryDataStok;
            if (data == null) {
                if (card.loading) {
                    showPlaceholders();
                }
                return;
            }

                tvTotalBarang.setText(data.getTotalBarang().toString());
                tvTotalStok.setText(data.getTotalStok().toString());
                tvTotalUang.setText("Total Uang : "+StringUtil.formatCurrency("Rp ",data.getTotalUang().toString()));




        }


    }
}
