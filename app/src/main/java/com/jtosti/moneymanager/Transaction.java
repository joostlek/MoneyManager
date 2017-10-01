package com.jtosti.moneymanager;


import android.content.Context;

/**
 * Created by joost on 4-9-17.
 */

public class Transaction {
    private int transactionId;
    private String name;
    private int amount;
    private String note;
    private int date;
    private int categoryId;
    private int sourceWalletId;
    private int destinationWalletId;

    public Transaction(Context context, String name, int amount, String note, int date, int categoryId, int sourceWalletId, int destinationWalletId) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        this.transactionId = databaseHandler.getTransactionsCount() + 1;
        databaseHandler.close();
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.date = date;
        this.categoryId = categoryId;
        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
    }

    public Transaction(int transactionId, String name, int amount, String note, int date, int categoryId, int sourceWalletId, int destinationWalletId) {
        this.transactionId = transactionId;
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.date = date;
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

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
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
