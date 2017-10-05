package com.jtosti.moneymanager;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityTransactionNew extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_new);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        int mode = getIntent().getExtras().getInt("mode");
        switch (mode) {
            case 1:
                mViewPager.setCurrentItem(0);
                break;
            case 2:
                mViewPager.setCurrentItem(1);
                break;
            case 3:
                mViewPager.setCurrentItem(2);
                break;
        }


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_transaction_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Fragment for incoming transactions
     */
    public static class FragmentIncoming extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FragmentIncoming() {
        }

        public static FragmentIncoming newInstance(int sectionNumber) {
            FragmentIncoming fragment = new FragmentIncoming();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_transaction_new_incoming, container, false);
            addWallets(rootView);
            addCategories(rootView);
            final EditText eName = rootView.findViewById(R.id.name);
            final EditText eAmount = rootView.findViewById(R.id.amount);
            final EditText eNote = rootView.findViewById(R.id.note);
            final Spinner eSelectWallet = rootView.findViewById(R.id.select_wallet);
            final Spinner eSelectCategory = rootView.findViewById(R.id.select_category);

            FloatingActionButton fab = rootView.findViewById(R.id.fab_next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = eName.getText().toString();
                    int amount = Integer.parseInt(eAmount.getText().toString());
                    String note = eNote.getText().toString();
                    int destinationId = eSelectWallet.getSelectedItemPosition();
                    int categoryId = eSelectCategory.getSelectedItemPosition();
                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                    long date = (new Date().getTime());
                    databaseHandler.addTransaction(new Transaction(getContext(), name, amount, note, date, categoryId, 0, destinationId));
                    databaseHandler.close();
                    Intent intent = new Intent(getContext(), ActivityWallet.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }

        public void addWallets(View view) {
            Spinner spinner = view.findViewById(R.id.select_wallet);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Wallet> wallets = databaseHandler.getAllWallets();
            List<String> list = new ArrayList<>();
            for (Wallet wallet: wallets) {
                list.add(wallet.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }

        public void addCategories(View view) {
            Spinner spinner = view.findViewById(R.id.select_category);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Category> categories = databaseHandler.getAllCategories();
            List<String> list = new ArrayList<>();
            for (Category category: categories) {
                list.add(category.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    /**
     * Fragment for transfer transactions
     */
    public static class FragmentTransfer extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FragmentTransfer() {
        }

        public static FragmentTransfer newInstance(int sectionNumber) {
            FragmentTransfer fragment = new FragmentTransfer();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_transaction_new_transfer, container, false);
            addWallets(rootView);
            addCategories(rootView);
            final EditText eName = rootView.findViewById(R.id.name);
            final EditText eAmount = rootView.findViewById(R.id.amount);
            final EditText eNote = rootView.findViewById(R.id.note);
            final Spinner eSelectWallet = rootView.findViewById(R.id.select_wallet);
            final Spinner eSelectDestWallet = rootView.findViewById(R.id.select_dest_wallet);
            final Spinner eSelectCategory = rootView.findViewById(R.id.select_category);

            FloatingActionButton fab = rootView.findViewById(R.id.fab_next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = eName.getText().toString();
                    int amount = Integer.parseInt(eAmount.getText().toString());
                    String note = eNote.getText().toString();
                    int sourceId = eSelectWallet.getSelectedItemPosition();
                    int destinationId = eSelectDestWallet.getSelectedItemPosition();
                    int categoryId = eSelectCategory.getSelectedItemPosition();
                    long date = (new Date().getTime());

                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                    databaseHandler.addTransaction(new Transaction(getContext(), name, amount, note, date, categoryId, sourceId, destinationId));
                    databaseHandler.close();
                    Intent intent = new Intent(getContext(), ActivityWallet.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }

        public void addWallets(View view) {
            Spinner spinner = view.findViewById(R.id.select_wallet);
            Spinner spinner1 = view.findViewById(R.id.select_dest_wallet);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Wallet> wallets = databaseHandler.getAllWallets();
            List<String> list = new ArrayList<>();
            for (Wallet wallet: wallets) {
                list.add(wallet.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
            spinner1.setAdapter(dataAdapter);
        }

        public void addCategories(View view) {
            Spinner spinner = view.findViewById(R.id.select_category);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Category> categories = databaseHandler.getAllCategories();
            List<String> list = new ArrayList<>();
            for (Category category: categories) {
                list.add(category.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }
    }

    /**
     * Fragment for outgoing transactions
     */
    public static class FragmentOutgoing extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public FragmentOutgoing() {
        }

        public static FragmentOutgoing newInstance(int sectionNumber) {
            FragmentOutgoing fragment = new FragmentOutgoing();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public void addWallets(View view) {
            Spinner spinner = view.findViewById(R.id.select_wallet);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Wallet> wallets = databaseHandler.getAllWallets();
            List<String> list = new ArrayList<>();
            for (Wallet wallet: wallets) {
                list.add(wallet.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }

        public void addCategories(View view) {
            Spinner spinner = view.findViewById(R.id.select_category);
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Category> categories = databaseHandler.getAllCategories();
            List<String> list = new ArrayList<>();
            for (Category category: categories) {
                list.add(category.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, list);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(dataAdapter);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_activity_transaction_new_outgoing, container, false);
            final EditText eName = rootView.findViewById(R.id.name);
            final EditText eAmount = rootView.findViewById(R.id.amount);
            final EditText eNote = rootView.findViewById(R.id.note);
            final Spinner eSelectWallet = rootView.findViewById(R.id.select_wallet);
            final Spinner eSelectCategory = rootView.findViewById(R.id.select_category);
            addWallets(rootView);
            addCategories(rootView);

            FloatingActionButton fab = rootView.findViewById(R.id.fab_next);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = eName.getText().toString();
                    int amount = Integer.parseInt(eAmount.getText().toString());
                    String note = eNote.getText().toString();
                    int sourceId = eSelectWallet.getSelectedItemPosition();
                    int categoryId = eSelectCategory.getSelectedItemPosition();
                    long date = (new Date().getTime());
                    DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
                    databaseHandler.addTransaction(new Transaction(getContext(), name, amount, note, date, categoryId, sourceId, 0));
                    databaseHandler.close();
                    Intent intent = new Intent(getContext(), ActivityWallet.class);
                    startActivity(intent);
                }
            });
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = null;
            switch(position) {
                case 0:
                    fragment = new FragmentOutgoing();
                    break;
                case 1:
                    fragment = new FragmentTransfer();
                    break;
                case 2:
                    fragment = new FragmentIncoming();
                    break;
                default:
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return getString(R.string.money_out);
                case 1:
                    return getString(R.string.money_trans);
                case 2:
                    return getString(R.string.money_in);
            }
            return null;
        }
    }
}
