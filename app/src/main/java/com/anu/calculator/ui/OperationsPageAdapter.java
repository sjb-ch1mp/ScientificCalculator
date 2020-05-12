package com.anu.calculator.ui;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Small extension to the FragmentPageAdapter class that adds some helper functions for finding
 * and adding fragments to the adapter. This helps when activities are manipulating fragments once
 * they have been created.
 *
 * @author: Michael Betterton (u6797866)
 */
public class OperationsPageAdapter extends FragmentPagerAdapter {

    /**
     * Private inner class that handles both fragments and their titles in a single class. This
     * class enables the OperationsPageAdapter to reference fragments by their title and retrieve
     * them as required.
     *
     * FragmentWrap implies that this is a simple wrapper for the Fragment Class, adding
     * capabilities it lacks.
     * @author: Michael Betterton (u6797866)
     */
    private class FragmentWrap{
        private String title;
        private Fragment fragment;

        /**
         * Constructor for the FragmentWrap that requires both a title and fragment.
         *
         * @author: Michael Betterton (u6797866)
         * @param title The title of the fragment.
         * @param fragment The Android UI fragment for this class.
         */
        FragmentWrap(String title, Fragment fragment){
            this.title = title;
            this.fragment = fragment;
        }

        /**
         * @author: Michael Betterton (u6797866)
         * @return The title of this Fragment
         */
        String getTitle(){
            return this.title;
        }

        /**
         * @author: Michael Betterton (u6797866)
         * @return The Android UI fragment for this fragment.
         */
        Fragment getFragment(){
            return this.fragment;
        }
    }

    private static final String TAG = "OperationsPageAdapter";
    private final List<FragmentWrap> mFragmentList = new ArrayList<FragmentWrap>();

    /**
     * Adds a fragment to the fragment helper classes for future use.
     *
     * @author: Michael Betterton (u6797866)
     * @param fragment The Fragment to add and store in the fragment manager.
     * @param title The title for the newly added fragment. This should be unique.
     */
    void addFragment(Fragment fragment, String title){
        FragmentWrap fragmentWrap = new FragmentWrap(title, fragment);
        mFragmentList.add(fragmentWrap);
        Log.d(TAG, "addFragment: "+title);
    }

    /**
     * Default constructor for the OperationsPageAdapter that just calls the fragment manager
     * constructor.
     *
     * @author: Michael Betterton (u6797866)
     * @param fm The fragment manager for the tablist.
     */
    OperationsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Given a position (index) of a fragment in the adapter, return the title of the fragment.
     *
     * @author: Michael Betterton (u6797866)
     * @param position The index of the fragment in the adapter.
     * @return The title of the fragment as a string.
     */
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }

    /**
     * Given a string that represents the title of a fragment, return the fragment that this
     * adapter is managing.
     *
     * @author: Michael Betterton (u6797866)
     * @param title The title of the fragment to search for.
     * @return A fragment for the provided title or null if it could not be found.
     */
    Fragment getFragmentByTitle(String title){
        for(FragmentWrap fragmentWrap:mFragmentList){
            if (fragmentWrap.getTitle().equals(title))
                return fragmentWrap.fragment;
        }
        return null;
    }

    /**
     * Finds a fragment at the given index that the adapter is managing.
     *
     * @author: Michael Betterton (u6797866)
     * @param position The index of the fragment to get.
     * @return The fragment at the provided index.
     */
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position).getFragment();
    }

    /**
     * Returns the count of the number of fragments that this adapter is managing.
     *
     * @author: Michael Betterton (u6797866)
     * @return The count of the total number of fragments this adapter is managing.
     */
    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
