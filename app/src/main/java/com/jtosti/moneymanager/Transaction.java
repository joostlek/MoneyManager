package com.jtosti.moneymanager;


import android.content.Context;

/**
 * Created by joost on 4-9-17.
 */

public class Transaction {
    private int transactionId;
    private String name;
    private double amount;
    private String note;
    private long date;
    private int categoryId;
    private int sourceWalletId;
    private int destinationWalletId;

    public Transaction(Context context, String name, double amount, String note, long date, int categoryId, int sourceWalletId, int destinationWalletId) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        this.transactionId = databaseHandler.getTransactionsCount() + 1;
        databaseHandler.close();
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.date = (date / 1000);
        this.categoryId = categoryId;
        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
    }

    public Transaction(int transactionId, String name, double amount, String note, long date, int categoryId, int sourceWalletId, int destinationWalletId) {
        this.transactionId = transactionId;
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.date = (date / 1000);
        this.categoryId = categoryId;
        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public long getDate() {
        return (date * 1000);
    }

    public void setDate(long date) {
        this.date = (date / 1000);
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getSourceWalletId() {
        return sourceWalletId;
    }

    public void setSourceWalletId(int sourceWalletId) {
        this.sourceWalletId = sourceWalletId;
    }

    public int getDestinationWalletId() {
        return destinationWalletId;
    }

    public void setDestinationWalletId(int destinationWalletId) {
        this.destinationWalletId = destinationWalletId;
    }
}
