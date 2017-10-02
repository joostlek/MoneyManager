package com.jtosti.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ActivityMain extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_wallet:
                    Intent intent = new Intent(ActivityMain.this, ActivityWallet.class);
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

        mTextMessage = findViewById(R.id.message);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        Gson gson = new Gson();
        databaseHandler.addWallet(new Wallet(1, "Debts", 900, 900, "EUR", new ArrayList<Integer>()));
        databaseHandler.addWallet(new Wallet(2, "", 900, 900, "EUR", new ArrayList<Integer>()));
        databaseHandler.addTransaction(new Transaction(this, "ss", 90, "lmao", 1, 1, 1, 2));
        mTextMessage.setText(Double.toString(databaseHandler.getWallet(1).getBalance(this)));

        List<Transaction> transactions = databaseHandler.getAllTransactions();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new TransactionArrayAdapter(transactions, 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
