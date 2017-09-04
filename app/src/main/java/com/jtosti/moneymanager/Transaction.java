package com.jtosti.moneymanager;

/**
 * Created by joost on 4-9-17.
 */

public class Transaction {
    private String name;
    private int amount;
    private String note;

    public Transaction(String name, int amount, String note) {
        this.name = name;
        this.amount = amount;
        this.note = note;
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
}
