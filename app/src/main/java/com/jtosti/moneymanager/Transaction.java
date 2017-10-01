package com.jtosti.moneymanager;


/**
 * Created by joost on 4-9-17.
 */

public class Transaction {
    private int transactionId;
    private String name;
    private int amount;
    private String note;
    private Category category;
    private int sourceWalletId;
    private int destinationWalletId;

    public Transaction(String name, int amount, String note, Category category, int sourceWalletId, int destinationWalletId) {
        this.transactionId = 1;
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.category = category;
        this.sourceWalletId = sourceWalletId;
        this.destinationWalletId = destinationWalletId;
    }

    public Transaction(int transactionId, String name, int amount, String note, Category category, int sourceWalletId, int destinationWalletId) {
        this.transactionId = transactionId;
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.category = category;
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
