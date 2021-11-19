package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.EditTextClickListener;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.SearchCard;
import com.eratani.android.utils.ToastUtil;

import butterknife.BindView;

@CardMap(SearchCard.class)
public class SearchCardProvider extends ItemViewProvider<SearchCard, SearchCardProvider.ViewHolder> {


    public SearchCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.search_card, parent, false),mOnItemClickListener);
    }


    public static class ViewHolder extends CommonVh<SearchCard> {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }

        @BindView(R.id.edSearch)
        EditText edSearch;

        @BindView(R.id.btAction)
        Button btAction;

        @Override
        public void bind(SearchCard card) {
            super.bind(card);

            btAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    ((EditTextClickListener) onItemClickListener).search(edSearch.getText().toString());

                }
            });

        }
    }
}
