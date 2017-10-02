package com.jtosti.moneymanager;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Currency;
import java.util.List;
import java.util.Locale;

public class ActivityWallet extends AppCompatActivity {

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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(ActivityWallet.this, ActivityMain.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_wallet:
                    return true;
                case R.id.navigation_notifications:
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.navigation_wallet);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DatabaseHandler databaseHandler = new DatabaseHandler(this);
        List<Wallet> wallets = databaseHandler.getAllWallets();
        wallets.remove(0);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), wallets);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), ActivityWalletNew.class);
//                startActivity(intent);
//            }
//        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_wallet, menu);
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
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static final String ARG_TOTAL = "total";
        private static final String ARG_CURRENCY = "currency";
        private static final String ARG_WALLETID = "walletid";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, double total, String currency, int walletId) {
            Log.v("test",  Integer.toString(sectionNumber));
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            args.putDouble(ARG_TOTAL, total);
            args.putString(ARG_CURRENCY, currency);
            args.putInt(ARG_WALLETID, walletId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_wallet, container, false);
            TextView total = (TextView) rootView.findViewById(R.id.total);
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
            try {
                if (getArguments().getString(ARG_CURRENCY).equals("USD")) {
                    formatter.setCurrency(Currency.getInstance(Locale.US));
                } else if (getArguments().getString(ARG_CURRENCY).equals("EUR")) {
                    formatter.setCurrency(Currency.getInstance(Locale.GERMANY));
                }
            } catch (NullPointerException e) {
                Log.e(getActivity().getPackageName(), e.toString());
            }
            DatabaseHandler databaseHandler = new DatabaseHandler(getContext());
            List<Transaction> transactions = databaseHandler.getTransactions(getArguments().getInt(ARG_WALLETID));
            RecyclerView recyclerView = rootView.findViewById(R.id.transactions);
            recyclerView.setAdapter(new TransactionArrayAdapter(transactions, getArguments().getInt(ARG_WALLETID),
                    getArguments().getString(ARG_CURRENCY)));
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            String moneyString = formatter.format(getArguments().getDouble(ARG_TOTAL));
            total.setText(String.format(getString(R.string.total), moneyString));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Wallet> wallets;
        public SectionsPagerAdapter(FragmentManager fm, List<Wallet> wallets) {
            super(fm);
            this.wallets = wallets;
        }


        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1, wallets.get(position).getBalance(getApplicationContext()),
                    wallets.get(position).getCurrency(), wallets.get(position).getWalletId());
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return wallets.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (!wallets.isEmpty()) {
                return wallets.get(position).getName();
            }
            return null;
        }
    }
}
