package com.jtosti.moneymanager;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

/**
 * Created by joost on 1-10-17.
 */

public class TransactionArrayAdapter extends RecyclerView.Adapter<TransactionArrayAdapter.ViewHolder> {

    private List<Transaction> items;
    private int itemLayout;
    private int walletId;
    private String currency;

    public TransactionArrayAdapter(List<Transaction> items, int walletId, String currency) {
        this.items = items;
        this.itemLayout = R.layout.transaction;
        this.walletId = walletId;
        this.currency = currency;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction item = items.get(position);
        Gson gson = new Gson();
        holder.name.setText(item.getName());
        Log.e("WalletId", Integer.toString(walletId));
        Log.e("DestinationWalletId", Integer.toString(item.getDestinationWalletId()));
        Log.e("SourceWalletId", Integer.toString(item.getSourceWalletId()));
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        try {
            if (currency.equals("USD")) {
                formatter.setCurrency(Currency.getInstance(Locale.US));
            } else if (currency.equals("EUR")) {
                formatter.setCurrency(Currency.getInstance(Locale.GERMANY));
            }
        } catch (NullPointerException e) {
            Log.e("lmao", e.toString());
        }
        String moneyString = formatter.format(item.getAmount());
        if (item.getSourceWalletId() == walletId) {
            holder.amount.setText(String.format("- %s", moneyString));
            holder.amount.setTextColor(Color.RED);
        } else if (item.getDestinationWalletId() == walletId) {
            holder.amount.setText(String.format("%s", moneyString));
            holder.amount.setTextColor(Color.parseColor("#558b2f"));
        }
    }

    @Override public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView amount;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            amount = (TextView) itemView.findViewById(R.id.amount);
        }
    }
}