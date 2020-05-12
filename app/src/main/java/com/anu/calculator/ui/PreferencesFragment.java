package com.anu.calculator.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;


import com.anu.calculator.R;

import java.util.Objects;

import static com.anu.calculator.ui.Util.*;

public class PreferencesFragment extends Fragment {

    private static final String TAG = "PREFERENCES_TAB";

    public static OperationsFragment newInstance() {
        return new OperationsFragment();
    }


    /**
     * Called to have the fragment instantiate its user interface view. All buttons in the fragment
     * have their OnClickListeners defined here as well.
     *
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @author: Michael Betterton (u6797866)
     * @return The fragments UI view.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        final EditText calculation_area = Objects.requireNonNull(getActivity()).findViewById(R.id.calculation_textarea);
        final MainActivity main = (MainActivity) Objects.requireNonNull(getActivity());

        final View rootView = inflater.inflate(R.layout.preferences_fragment, container, false);

        final EditText precision = (EditText) rootView.findViewById(R.id.precision_text);
        precision.setText((Integer) R.string.default_precision);
        precision.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    int input = Integer.parseInt(precision.getText().toString());
                    // If the precision is >16, set it to 16 because reasons
                    if (input > 16) input = 16;
                    main.put("precision",input);
                    // Hide the soft keyboard as we don't need it anymore
                    InputMethodManager inputManager = (InputMethodManager)
                    Objects.requireNonNull(getContext()).getSystemService(Context.INPUT_METHOD_SERVICE);
                    assert inputManager != null;
                    inputManager.toggleSoftInput(0, 0);
                    // Remove the focus from the edit text entirely
                    precision.clearFocus();
                    return true;
                }
                return false;
            }
        });

        Switch sw = (Switch) rootView.findViewById(R.id.degrees_switch);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    main.put("degrees",true);
                } else {
                    main.put("degrees",false);
                }
            }
        });

        return rootView;
    }

}
