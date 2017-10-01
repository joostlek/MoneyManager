package com.jtosti.moneymanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by joost on 1-10-17.
 */

public class TransactionArrayAdapter extends RecyclerView.Adapter<TransactionArrayAdapter.ViewHolder> {

    private List<Transaction> items;
    private int itemLayout;
    private int walletId;

    public TransactionArrayAdapter(List<Transaction> items, int walletId) {
        this.items = items;
        this.itemLayout = R.layout.transaction;
        this.walletId = walletId;
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(v);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        Transaction item = items.get(position);
        holder.name.setText(item.getName());
        if (item.getSourceWalletId() == walletId) {
            holder.amount.setText("-" + Integer.toString(item.getAmount()));
        } else if (item.getDestinationWalletId() == walletId) {
            holder.amount.setText(Integer.toString(item.getAmount()));
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