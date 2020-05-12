package com.anu.calculator.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.anu.calculator.R;

import com.anu.calculator.utilities.History;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

import static com.anu.calculator.utilities.History.getInstance;


public class MainActivity extends AppCompatActivity implements HistoryMessenger {

    private static final String TAG = "MainActivity";
    public static final String PREFS_NAME = "PREFERENCES";
    private static final String fileName = "history.dat";

    OperationsPageAdapter mOperationsPageAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        mOperationsPageAdapter = new OperationsPageAdapter(getSupportFragmentManager());

        // Instantiate a view pager
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setOffscreenPageLimit(100);
        setupViewOperationsPager(mViewPager);

        // Setup the Tab Layout
        TabLayout tabLayout = (TabLayout) findViewById(R.id.operations_tabs);
        tabLayout.setupWithViewPager(mViewPager);

        // Setup the Floating button by Siwei Wu
        createFloatingButton();


        // Focus on the Calculation Text Area
        final EditText calculation_area = findViewById(R.id.calculation_textarea);
        calculation_area.setShowSoftInputOnFocus(false);

        // Set the shared preferences to the default values
        put("eval",false); // Set the evaluation state to false.
        put("degrees",false); // Set the slider to use degrees as false
        put("precision", 20); // Set the precision value to 20 places by default.
    }


    /**
     * This is the method to create the floating button to navigate between calculator activity and graph activity
     *
     * @author: Siwei Wu (u6735397)
    */    public void createFloatingButton() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab_graph);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toGraph = new Intent(MainActivity.this, GraphActivity.class);
                toGraph.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(toGraph, 0);
            }
        });
    }

    /**
     * This is the interface that allows other fragments to communicate with the history fragment.
     * By design, child fragments should not directly communicate, but instead communicate with the
     * parent activity, which in this case is the MainActivity (this).
     *
     * This method finds the history fragment and calls it's addHistory method which it then deals
     * with the repercussions of.
     *
     * @author: Michael Betterton (u6797866)
     * @param data The data to pass to the history fragment
     */
    @Override
    public void sendHistory(String data) {
        // Get the history fragment by find it in the page adapter
        HistoryFragment f = (HistoryFragment) mOperationsPageAdapter.getFragmentByTitle(getString(R.string.tab_history));
        // Send it the new history message received from the child fragment.
        f.addHistory(data);
    }

    /**
     * Takes a ViewPager and adds all Fragments to the viewPager by first adding them to a
     * OperationsPageAdapter, which is a FragmentPagerAdapter. The OperationsPageAdapter extends
     * FragmentPagerAdapter and adds several useful methods for retrieval of fragments based on
     * name, tag and id. It also keeps track of the total number of fragments in operation.
     *
     * @author: Michael Betterton (u6797866)
     * @param viewPager The ViewPager to add Fragments to.
     */
    private void setupViewOperationsPager(ViewPager viewPager) {
        mOperationsPageAdapter.addFragment(new DigitFragment(), getString(R.string.tab_basic));
        mOperationsPageAdapter.addFragment(new OperationsFragment(), getString(R.string.tab_scientific));
        mOperationsPageAdapter.addFragment(new HistoryFragment(), getString(R.string.tab_history));
        mOperationsPageAdapter.addFragment(new FunctionFragment(), getString(R.string.tab_function));
        mOperationsPageAdapter.addFragment(new PreferencesFragment(), getString(R.string.tab_preferences));
        viewPager.setAdapter(mOperationsPageAdapter);
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Boolean value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, String value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Set<String> value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putStringSet(key, value);
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Long value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Float value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * As the shared preferences class has no concept of double storage (I know right?), we instead
     * convert the double to a Long as the bits for the Double. This conversion will be undone on
     * retrieval.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Double value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, Double.doubleToLongBits(value));
        editor.apply();
    }

    /**
     * Inserts into the private shared preferences the provided key and value. Note that this is a
     * unencrypted form of storage and should only be used for generic preferences and not secrets.
     *
     * @param key The key to identify the preference by.
     * @param value The primative value to store.
     * @author: Michael Betterton (u6797866)
     */
    public void put(String key, Integer value) {
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);;
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of false is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Boolean getBoolean(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getBoolean(key, false);
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of 0 is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Long getLong(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getLong(key, 0);
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of 0 is returned.
     *
     * As the shared preferences class has no concept of double storage (I know right?), the value
     * is retrieved as a long and converted back to a double. This long to double conversion has no
     * precision loss as we actually store the double as its bit representation.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Double getDouble(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        long doubleBits = settings.getLong(key, 0);
        return Double.longBitsToDouble(doubleBits);
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of 0 is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Float getFloat(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getFloat(key, 0);
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of "" (empty string) is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public String getString(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(key, "");
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of a empty HashSet is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Set<String> getStringSet(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getStringSet(key, new HashSet<String>(0));
    }

    /**
     * Returns the saved preferences from the preferences file for the given key. If no  matching
     * key is found, a default value of 0 is returned.
     *
     * @param key The key to identify the preference by.
     * @author: Michael Betterton (u6797866)
     */
    public Integer getInt(String key) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key, 0);
    }


    /**
     * Returns true if the shared preferences file contains the provided key in its storage.
     *
     * @param key The key to check for in the shared preferences storage.
     * @return Boolean True if it exists, otherwise False.
     */
    public Boolean contains(String key){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        return settings.contains(key);
    }
}
