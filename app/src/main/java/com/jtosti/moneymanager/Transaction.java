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
    private Wallet sourceWallet;
    private Wallet destinationWallet;

    public Transaction(String name, int amount, String note, Category category, Wallet sourceWallet, Wallet destinationWallet) {
        this.transactionId = 1;
        this.name = name;
        this.amount = amount;
        this.note = note;
        this.category = category;
        this.sourceWallet = sourceWallet;
        this.destinationWallet = destinationWallet;
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

    public Wallet getSourceWallet() {
        return sourceWallet;
    }

    public void setSourceWallet(Wallet sourceWallet) {
        this.sourceWallet = sourceWallet;
    }

    public Wallet getDestinationWallet() {
        return destinationWallet;
    }

    public void setDestinationWallet(Wallet destinationWallet) {
        this.destinationWallet = destinationWallet;
    }
}
