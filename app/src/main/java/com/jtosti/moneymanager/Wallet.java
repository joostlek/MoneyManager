package com.jtosti.moneymanager;

import android.content.Context;

import java.util.List;

/**
 * Created by joost on 30-9-17.
 */

public class Wallet {
    private int walletId;
    private String name;
    private double startBalance;
    private double balance;
    private String currency;
    private List<Integer> transactions;

    public Wallet(int walletId, String name, double startBalance, double balance, String currency, List<Integer> transactions) {
        this.walletId = walletId;
        this.name = name;
        this.startBalance = startBalance;
        this.balance = balance;
        this.currency = currency;
        this.transactions = transactions;
    }

    public Wallet(int walletId, String name, double startBalance, String currency, List<Integer> transactions) {
        this.walletId = walletId;
        this.name = name;
        this.balance = startBalance;
        this.currency = currency;
        this.startBalance = startBalance;
        this.transactions = transactions;
    }

    public Wallet(Context context, String name, double startBalance, String currency, List<Integer> transactions) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        this.walletId = databaseHandler.getWalletCount() + 1;
        databaseHandler.close();
        this.name = name;
        this.startBalance = startBalance;
        this.balance = startBalance;
        this.currency = currency;
        this.transactions = transactions;
    }

    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(double startBalance) {
        this.startBalance = startBalance;
    }

    public double getBalance(Context context) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        balance = startBalance;
        for (Integer transactionId : transactions) {
            Transaction transaction = databaseHandler.getTransaction(transactionId);
            if (transaction.getSourceWalletId() == this.walletId) {
                this.balance = this.balance - transaction.getAmount();
            } else if (transaction.getDestinationWalletId() == this.walletId) {
                this.balance = this.balance + transaction.getAmount();
            }
        }
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Integer> getTransactionIds() {
        return transactions;
    }

    public void setTransactions(List<Integer> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getSourceWalletId() == this.walletId) {
            this.balance = this.balance - transaction.getAmount();
        } else if (transaction.getDestinationWalletId() == this.walletId) {
            this.balance = this.balance + transaction.getAmount();
        }
        transactions.add(transaction.getTransactionId());
    }
}