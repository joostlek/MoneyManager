package com.jtosti.moneymanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by joost on 5-10-17.
 */

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Transaction> mData;
    private Bundle args;

    public void add(Transaction s,int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position,s);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView amount;
        SimpleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.name);
            amount = view.findViewById(R.id.amount);

        }
    }

    public SimpleAdapter(Context context, List<Transaction> data, Bundle args) {
        mContext = context;
        mData = data;
        this.args = args;
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.transaction, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        if (args.getString("currency").equals("USD")) {
            formatter.setCurrency(Currency.getInstance(Locale.US));
        } else if (args.getString("currency").equals("EUR")) {
            formatter.setCurrency(Currency.getInstance(Locale.GERMANY));
        }
        String moneyString = formatter.format(mData.get(position).getAmount());
        if (mData.get(position).getSourceWalletId() == args.getInt("walletid")) {
            holder.amount.setText(String.format("- %s", moneyString));
            holder.amount.setTextColor(Color.RED);
        } else if (mData.get(position).getDestinationWalletId() == args.getInt("walletid")) {
            holder.amount.setText(String.format("%s", moneyString));
            holder.amount.setTextColor(Color.parseColor("#558b2f"));
        }
        holder.title.setText(mData.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
