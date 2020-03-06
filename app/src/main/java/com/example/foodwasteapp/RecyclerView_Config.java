package com.example.foodwasteapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static androidx.core.content.ContextCompat.startActivity;
import static java.lang.String.valueOf;

public class RecyclerView_Config {
    FirebaseAuth mAuth;
    private static FirebaseUser user;
    private Context mContext;
    private ItemsAdapter mItemsAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Item> items, List<String> keys) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
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
        private String imageUrl;
        private String key;

        public ItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
            inflate(R.layout.fridge_item, parent, false));

            mName = itemView.findViewById(R.id.name_txtView);
            mQuantity = itemView.findViewById(R.id.quantity_txtView);
            mStorage = itemView.findViewById(R.id.storage_txtView);
            mExpiryDate = itemView.findViewById(R.id.expiration_date);
            ItemImage = itemView.findViewById(R.id.ItemImage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (user != null) {
                        Intent intent = new Intent(mContext, ItemEdit.class);
                        intent.putExtra("key",key);
                        intent.putExtra("name",mName.getText().toString());
                        intent.putExtra("quantity", mQuantity.getText().toString());
                        intent.putExtra("storage", mStorage.getText().toString());
                        intent.putExtra("expiryDate", mExpiryDate.getText().toString());
                        intent.putExtra("image", imageUrl);

                        mContext.startActivity(intent);
                    } else {
                        mContext.startActivity(new Intent (mContext, SignIn.class));
                    }
                }
            });
        }
        @SuppressLint("ResourceAsColor")
        public void bind(Item item, String key){
            String currentDate = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
            String expiryDate = item.getExpiryDate();

            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
                Date currentDate1 = sdf.parse(currentDate);
                Date expiryDate1 = sdf.parse(expiryDate);
                long daysToExpiry = expiryDate1.getTime() - currentDate1.getTime();
                daysToExpiry = TimeUnit.DAYS.convert(daysToExpiry, TimeUnit.MILLISECONDS);
                
                if (daysToExpiry > 0) {
                    if (daysToExpiry <= 2){
                        mExpiryDate.setTextColor(Color.parseColor("#FFA500"));
                    }
                }  else if (daysToExpiry == 0) {
                    mExpiryDate.setTextColor(Color.parseColor("#FF6347"));

                } else if (daysToExpiry < 0) {
                    mExpiryDate.setTextColor(Color.parseColor("#B22222"));
                    mExpiryDate.setPaintFlags(mExpiryDate.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

                }
                    mName.setText(item.getName());
                    mQuantity.setText(item.getQuantity());
                    mStorage.setText(item.getStorage());
                    mExpiryDate.setText(item.getExpiryDate());
                    imageUrl = item.getImage();
                    Picasso.with(mContext).load(imageUrl).into(ItemImage);
                    this.key = key;
            } catch (Exception e) {
                e.printStackTrace();
            }

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

    public static void Logout() {
        user = null;
    }
}
