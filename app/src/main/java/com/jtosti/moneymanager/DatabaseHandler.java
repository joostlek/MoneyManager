package com.jtosti.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by joost on 1-10-17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MoneyManager";
    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String KEY_ID = "transactionId";
    private static final String KEY_NAME = "name";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_NOTE = "note";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_SOURCEWALLET = "sourceWallet";
    private static final String KEY_DESTINATIONWALLET = "destinationWallet";

    public DatabaseHandler(Context context) {
        super(context, "moneyManager", (SQLiteDatabase.CursorFactory)null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE transactions(transactionId INTEGER PRIMARY KEY,name TEXT,amount INTEGER,note TEXT,category int,sourceWalletId INT, destinationWalletId INT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transactions");
        this.onCreate(db);
    }

    void addTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put("transactionId", transaction.getTransactionId());
        values.put("name", transaction.getName());
        values.put("amount", transaction.getAmount());
        values.put("note", transaction.getNote());
        values.put("category", gson.toJson(transaction.getCategory()));
        values.put("sourceWalletId", transaction.getSourceWalletId());
        values.put("destinationWalletId", transaction.getDestinationWalletId());
        db.insert("transactions", (String)null, values);
        db.close();
    }

    Transaction getTransaction(int transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("transactions", new String[]{"transactionId", "name", "amount", "note", "category", "sourceWalletId", "destinationWalletId"}, "transactionId=?", new String[]{String.valueOf(transactionId)}, (String)null, (String)null, (String)null, (String)null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        Gson gson = new Gson();
        Transaction transaction = new Transaction(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), gson.fromJson(cursor.getString(4), Category.class), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
        return transaction;
    }

    public List<Transaction> getAllTransactions() {
        ArrayList transactionList = new ArrayList();
        String selectQuery = "SELECT  * FROM transactions";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, (String[])null);
        if(cursor.moveToFirst()) {
            do {
                Gson gson = new Gson();
                Transaction transaction = new Transaction(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), cursor.getString(3), gson.fromJson(cursor.getString(4), Category.class), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)));
                transactionList.add(transaction);
            } while(cursor.moveToNext());
        }

        return transactionList;
    }

    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put("transactionId", transaction.getTransactionId());
        values.put("name", transaction.getName());
        values.put("amount", transaction.getAmount());
        values.put("note", transaction.getNote());
        values.put("category", gson.toJson(transaction.getCategory()));
        values.put("sourceWalletId", transaction.getSourceWalletId());
        values.put("destinationWalletId", transaction.getDestinationWalletId());
        return db.update("transactions", values, "transactionId = ?", new String[]{String.valueOf(transaction.getTransactionId())});
    }

    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("transaction", "transactionId = ?", new String[]{String.valueOf(transaction.getTransactionId())});
        db.close();
    }

    public int getTransactionsCount() {
        String countQuery = "SELECT  * FROM transactions";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, (String[])null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
