package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.ReyclerviewGlobalCard;
import com.eratani.android.utils.AppUtilNew;
import com.eratani.android.utils.LogUtil;

import java.util.List;

import butterknife.BindView;

@CardMap(ReyclerviewGlobalCard.class)
public class ReycleviewGlobalCardProvider extends ItemViewProvider<ReyclerviewGlobalCard, ReycleviewGlobalCardProvider.ViewHolder> {


    public ReycleviewGlobalCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_card_recyclerview_global, parent, false), mOnItemClickListener);
    }

    public static class ViewHolder extends CommonVh<ReyclerviewGlobalCard> implements CardAdapter.OnItemClickListener {

        @BindView(R.id.rv)
        RecyclerView rv;

        CardAdapter adapter;


        @Override
        public void onItemOnclick(int position) {

            if (baseCard == null || baseCard.listData == null || baseCard.listData.isEmpty()) {
                return;
            }
//
//            if (this.onItemClickListener instanceof FoldTextCardOnClickListener) {
//                ArrayList<String> photos = new ArrayList<>(baseCard.listData.size());
//                for (ImageBean imageBean : baseCard.listData) {
//                    photos.add(imageBean.getCompatibleImageUrl());
//                }
//                ((FoldTextCardOnClickListener) this.onItemClickListener).onItemPhotoClick(getAdapterPosition(), position, photos, false);
//            }
        }

        public ViewHolder(View itemView) {
            this(itemView, null);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);


        }

        @Override
        public void bind(ReyclerviewGlobalCard card) {
            super.bind(card);

            AppUtilNew.setRecycleViewGlobal(itemView.getContext(), rv, card.cardProvider, card.listData, card.spanCount, 6);

        }


        @Override
        public void bind(ReyclerviewGlobalCard card, @NonNull List payloads) {
            super.bind(card, payloads);
            LogUtil.logE("onPreDraw", "bind payloads=" + payloads.size());
            adapter.clearList();
            adapter.addAll(card.listData);
        }
    }

}
