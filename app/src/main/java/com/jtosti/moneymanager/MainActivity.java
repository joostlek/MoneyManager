package com.jtosti.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(MainActivity.this, WalletActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Gson gson = new Gson();
        databaseHandler.addWallet(new Wallet(1, "ss", 900, 900, new ArrayList<Integer>()));
        databaseHandler.addWallet(new Wallet(2, "ss", 900, 900, new ArrayList<Integer>()));
        databaseHandler.addTransaction(new Transaction(this, "ss", 90, "lmao", 1, 1, 1, 2));
        mTextMessage.setText(Integer.toString(databaseHandler.getWallet(1).getBalance(this)));
        Log.v(this.getPackageName(), gson.toJson(databaseHandler.getWallet(1).getTransactionIds()));
        Log.v(this.getPackageName(), Integer.toString(databaseHandler.getTransactionsCount()));
        List<Transaction> transactions = databaseHandler.getAllTransactions();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new TransactionArrayAdapter(transactions, 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
