package com.example.foodwasteapp;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private ItemsAdapter mItemsAdapter;
    private ItemsAdapter emptyAdapter;


    public void setConfig(RecyclerView recyclerView, Context context, List<Item> items, List<String> keys) {
        mContext = context;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mItemsAdapter = new ItemsAdapter(items, keys);
        recyclerView.setAdapter(mItemsAdapter);
    }

    class ItemView extends RecyclerView.ViewHolder{
        private TextView mTitle;
        private TextView mQuantity;
        private TextView mStorage;

        private String key;

        public ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
            inflate(R.layout.fridge_item, parent, false));

            mTitle = itemView.findViewById(R.id.title_txtView);
            mQuantity = itemView.findViewById(R.id.quantity_txtView);
            mStorage = itemView.findViewById(R.id.storage_txtView);
        }
        public void bind(Item item, String key){
            mTitle.setText(item.getId());
            mQuantity.setText(String.valueOf(item.getQuantity()));
            mStorage.setText(item.getStorage());
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
