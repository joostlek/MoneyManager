package com.jtosti.moneymanager;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

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
        String CREATE_TRANSACTIONS_TABLE = "CREATE TABLE transactions(transactionId INTEGER PRIMARY KEY,name TEXT,amount REAL,note TEXT,date INT,categoryId INT,sourceWalletId INT, destinationWalletId INT)";
        String CREATE_WALLETS_TABLE = "CREATE TABLE wallets(walletId INTEGER PRIMARY KEY,name TEXT,startBalance REAL,currency TEXT,transactions TEXT)";
        db.execSQL(CREATE_TRANSACTIONS_TABLE);
        db.execSQL(CREATE_WALLETS_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS transactions");
        db.execSQL("DROP TABLE IF EXISTS wallets");
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
        values.put("date", transaction.getDate());
        values.put("categoryId", transaction.getCategoryId());
        values.put("sourceWalletId", transaction.getSourceWalletId());
        values.put("destinationWalletId", transaction.getDestinationWalletId());
        db.insert("transactions", (String)null, values);
        Wallet wallet = this.getWallet(transaction.getSourceWalletId());
        wallet.addTransaction(transaction);
        this.updateWallet(wallet);
        Wallet wallet1 = this.getWallet(transaction.getDestinationWalletId());
        wallet1.addTransaction(transaction);
        this.updateWallet(wallet1);
        db.close();
    }

    void addWallet(Wallet wallet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put("walletId", wallet.getWalletId());
        values.put("name", wallet.getName());
        values.put("startBalance", wallet.getStartBalance());
        values.put("currency", wallet.getCurrency());
        values.put("transactions", gson.toJson(wallet.getTransactionIds()));
        db.insert("wallets", (String)null, values);
        db.close();
    }

    Transaction getTransaction(int transactionId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("transactions", new String[]{"transactionId", "name", "amount", "note", "date", "categoryId", "sourceWalletId", "destinationWalletId"}, "transactionId=?", new String[]{String.valueOf(transactionId)}, (String)null, (String)null, (String)null, (String)null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        Transaction transaction = new Transaction(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)));
        cursor.close();
        return transaction;
    }

    Wallet getWallet(int walletId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("wallets", new String[]{"walletId", "name", "startBalance", "currency", "transactions"}, "walletId=?", new String[]{String.valueOf(walletId)}, (String)null, (String)null, (String)null, (String)null);
        if(cursor != null) {
            cursor.moveToFirst();
        }
        Gson gson = new Gson();
        Wallet wallet = new Wallet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), (ArrayList<Integer>) gson.fromJson(cursor.getString(4), new TypeToken<ArrayList<Integer>>(){}.getType()));
        cursor.close();
        return wallet;
    }

    public List<Transaction> getAllTransactions() {
        ArrayList transactionList = new ArrayList();
        String selectQuery = "SELECT  * FROM transactions";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, (String[])null);
        if(cursor.moveToFirst()) {
            do {
                Transaction transaction = new Transaction(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), Integer.parseInt(cursor.getString(4)), Integer.parseInt(cursor.getString(5)), Integer.parseInt(cursor.getString(6)), Integer.parseInt(cursor.getString(7)));
                transactionList.add(transaction);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return transactionList;
    }

    public List<Wallet> getAllWallets() {
        ArrayList walletList = new ArrayList();
        String selectQuery = "SELECT  * FROM wallets";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, (String[])null);
        if(cursor.moveToFirst()) {
            do {
                Gson gson = new Gson();
                Wallet wallet = new Wallet(Integer.parseInt(cursor.getString(0)), cursor.getString(1), Double.parseDouble(cursor.getString(2)), cursor.getString(3), (ArrayList<Integer>) gson.fromJson(cursor.getString(4), new TypeToken<ArrayList<Integer>>(){}.getType()));
                walletList.add(wallet);
            } while(cursor.moveToNext());
        }
        cursor.close();
        return walletList;
    }

    public int updateTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put("transactionId", transaction.getTransactionId());
        values.put("name", transaction.getName());
        values.put("amount", transaction.getAmount());
        values.put("note", transaction.getNote());
        values.put("categoryId", gson.toJson(transaction.getCategoryId()));
        values.put("sourceWalletId", transaction.getSourceWalletId());
        values.put("destinationWalletId", transaction.getDestinationWalletId());
        return db.update("transactions", values, "transactionId = ?", new String[]{String.valueOf(transaction.getTransactionId())});
    }

    public int updateWallet(Wallet wallet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        Gson gson = new Gson();
        values.put("walletId", wallet.getWalletId());
        values.put("name", wallet.getName());
        values.put("startBalance", wallet.getStartBalance());
        values.put("currency", wallet.getCurrency());
        values.put("transactions", gson.toJson(wallet.getTransactionIds()));
        return db.update("wallets", values, "walletId = ?", new String[]{String.valueOf(wallet.getWalletId())});
    }


    public void deleteTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("transactions", "transactionId = ?", new String[]{String.valueOf(transaction.getTransactionId())});
        db.close();
    }

    public void deleteWallet(Wallet wallet) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("wallets", "walletId = ?", new String[]{String.valueOf(wallet.getWalletId())});
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

    public int getWalletCount() {
        String countQuery = "SELECT  * FROM wallets";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, (String[])null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
}
