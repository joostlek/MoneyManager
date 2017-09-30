package com.jtosti.moneymanager;

import java.util.List;

/**
 * Created by joost on 30-9-17.
 */

public class Wallet {
    private String name;
    private int startBalance;
    private int balance;
    private List<Integer> transactions;

    public Wallet(String name, int startBalance, int balance, List<Integer> transactions) {
        this.name = name;
        this.startBalance = startBalance;
        this.balance = balance;
        this.transactions = transactions;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public List<Integer> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Integer> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (transaction.getSourceWallet() == this) {
            this.balance = this.balance - transaction.getAmount();
        } else if (transaction.getDestinationWallet() == this) {
            this.balance = this.balance + transaction.getAmount();
        }
        transactions.add(transaction.getTransactionId());
    }
}