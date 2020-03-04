package com.example.foodwasteapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static java.lang.String.valueOf;

public class RecyclerView_Config {
    private Context mContext;
    private ItemsAdapter mItemsAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Item> items, List<String> keys) {
        mContext = context;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mItemsAdapter = new ItemsAdapter(items, keys);
        recyclerView.setAdapter(mItemsAdapter);
    }

    class ItemView extends RecyclerView.ViewHolder{
        private TextView mName;
        private TextView mQuantity;
        private TextView mStorage;
        private TextView mExpiryDate;
        private ImageView ItemImage;

        private String key;

        public ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
            inflate(R.layout.fridge_item, parent, false));

            mName = itemView.findViewById(R.id.name_txtView);
            mQuantity = itemView.findViewById(R.id.quantity_txtView);
            mStorage = itemView.findViewById(R.id.storage_txtView);
            mExpiryDate = itemView.findViewById(R.id.expiration_date);
            //ItemImage = itemView.findViewById(R.id.ItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ItemEdit.class);
                    intent.putExtra("key",key);
                    intent.putExtra("name",mName.getText().toString());
                    intent.putExtra("quantity", mQuantity.getText().toString());
                    intent.putExtra("storage", mStorage.getText().toString());
                    intent.putExtra("expiryDate", mExpiryDate.getText().toString());

                    mContext.startActivity(intent);
                }
            });
        }
        public void bind(Item item, String key){
            mName.setText(item.getName());
            mQuantity.setText(item.getQuantity());
            mStorage.setText(item.getStorage());
            mExpiryDate.setText(item.getExpiryDate());
            this.key = key;

        }
    }
   public class ItemsAdapter extends RecyclerView.Adapter<ItemView> {
        private List<Item> itemList;
        private List<String> mKeys;


        public ItemsAdapter(List<Item> mItemList, List<String> mKeys) {
            this.itemList = mItemList;
            this.mKeys = mKeys;
        }

        @Override
        public ItemView onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ItemView(parent);
        }

        @Override
        public void onBindViewHolder(ItemView holder, int position) {
            holder.bind(itemList.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return itemList.size();
        }
    }
}
