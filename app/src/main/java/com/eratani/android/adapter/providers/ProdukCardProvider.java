package com.eratani.android.adapter.providers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.eratani.android.R;
import com.eratani.android.adapter.CardAdapter;
import com.eratani.android.adapter.CardMap;
import com.eratani.android.adapter.CommonVh;
import com.eratani.android.adapter.ItemViewProvider;
import com.eratani.android.adapter.card.ProdukCard;
import com.eratani.android.configapp.room.ProdukModel;
import com.eratani.android.configapp.router.IntentUtil;
import com.eratani.android.utils.PicassoUtil;
import com.eratani.android.utils.StringUtil;

import butterknife.BindView;

@CardMap(ProdukCard.class)
public class ProdukCardProvider extends ItemViewProvider<ProdukCard, ProdukCardProvider.ViewHolder> {


    public ProdukCardProvider(CardAdapter.OnItemClickListener listener) {
        super(listener);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.item_produk, parent, false), mOnItemClickListener);
    }


    public static class ViewHolder extends CommonVh<ProdukCard> {
        @BindView(R.id.tvNameProduk)
        TextView tvJudul;

        @BindView(R.id.tvHarga)
        TextView tvHarga;

        @BindView(R.id.tvStok)
        TextView tvStok;


        @BindView(R.id.ic_image)
        ImageView ic_image;

        @BindView(R.id.bt_jual)
        Button bt_jual;


        public ViewHolder(View itemView) {
            this(itemView, null);
        }

        public ViewHolder(View itemView, CardAdapter.OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }


        @Override
        public void bind(ProdukCard card) {
            super.bind(card);

            ProdukModel produk = card.produkModel;
            if (produk == null) {
                if (card.loading) {
                    showPlaceholders();
                }
                return;
            }

            PicassoUtil.load(produk.getImage()).centerCrop().fit().into(ic_image);
            tvJudul.setText(produk.getNamaProduk());
            tvHarga.setText(StringUtil.formatCurrency("Rp ",produk.getHarga().toString()));
            tvStok.setText("Stok : "+produk.getStok());

            bt_jual.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentUtil.intentToJualProduk(itemView.getContext(),produk);
                }
            });

        }


    }
}
