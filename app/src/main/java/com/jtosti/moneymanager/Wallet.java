package com.jtosti.moneymanager;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

/**
 * Created by joost on 30-9-17.
 */

public class Wallet {
    private int walletId;
    private String name;
    private int startBalance;
    private int balance;
    private List<Integer> transactions;

    public Wallet(int walletId, String name, int startBalance, int balance, List<Integer> transactions) {
        this.walletId = walletId;
        this.name = name;
        this.startBalance = startBalance;
        this.balance = balance;
        this.transactions = transactions;
    }

    public Wallet(int walletId, String name, int startBalance, List<Integer> transactions) {
        this.walletId = walletId;
        this.name = name;
        this.balance = startBalance;
        this.startBalance = startBalance;
        this.transactions = transactions;
    }

    public Wallet(Context context, String name, int startBalance, List<Integer> transactions) {
        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        this.walletId = databaseHandler.getWalletCount() + 1;
        databaseHandler.close();
        this.name = name;
        this.startBalance = startBalance;
        this.balance = startBalance;
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

    public int getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(int startBalance) {
        this.startBalance = startBalance;
    }

    public int getBalance(Context context) {
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

    public void setBalance(int balance) {
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