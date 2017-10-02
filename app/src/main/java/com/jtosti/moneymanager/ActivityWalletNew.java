package com.jtosti.moneymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityWalletNew extends AppCompatActivity {
    private EditText name;
    private EditText startBalance;
    private FloatingActionButton next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_new);

        final EditText name = findViewById(R.id.name);
        final EditText startBalance = findViewById(R.id.start_balance);
        final Spinner spinner = findViewById(R.id.spinner);
        FloatingActionButton next = findViewById(R.id.floatingActionButton);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (startBalance.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), R.string.insert_start_balance, Toast.LENGTH_LONG).show();
                    return;
                } else if (!startBalance.getText().toString().matches("\\d+(.\\d{1,2})")) {
                    Toast.makeText(getApplicationContext(), R.string.insert_numbers, Toast.LENGTH_LONG).show();
                    return;
                }
                if (name.getText().toString().matches("")) {
                    Toast.makeText(getApplicationContext(), R.string.insert_name, Toast.LENGTH_LONG).show();
                    return;
                }
                DatabaseHandler databaseHandler = new DatabaseHandler(getApplicationContext());
                databaseHandler.addWallet(new Wallet(getApplicationContext(), name.getText().toString(), Double.parseDouble(startBalance.getText().toString()), spinner.toString(), new ArrayList<Integer>()));
                Intent intent = new Intent(getApplicationContext(), ActivityWallet.class);
                startActivity(intent);
            }
        });
    }
}
