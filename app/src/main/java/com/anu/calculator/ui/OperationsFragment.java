package com.anu.calculator.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.anu.calculator.R;

import java.util.Objects;

import static com.anu.calculator.ui.Util.*;

public class OperationsFragment extends Fragment {

    private static final String TAG = "OPERATIONS_TAB";

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

        View rootView = inflater.inflate(R.layout.operations_fragment, container, false);

        Button sin = rootView.findViewById(R.id.sin);
        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.sin);
                addText(calculation_area, input, main, false);
            }
        });

        Button cos = rootView.findViewById(R.id.cos);
        cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.cos);
                addText(calculation_area, input, main, false);
            }
        });

        Button tan = rootView.findViewById(R.id.tan);
        tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.tan);
                addText(calculation_area, input, main, false);
            }
        });

        Button squared = rootView.findViewById(R.id.squared);
        squared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.squared);
                addText(calculation_area, input, main, false);
            }
        });

        Button natural_log = rootView.findViewById(R.id.natural_log);
        natural_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.natural_log);
                addText(calculation_area, input, main, false);
            }
        });

        Button log_10 = rootView.findViewById(R.id.log10);
        log_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.log10);
                addText(calculation_area, input, main, false);
            }
        });

        Button power = rootView.findViewById(R.id.power);
        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.power);
                addText(calculation_area, input, main, false);
            }
        });

        Button euler = rootView.findViewById(R.id.euler);
        euler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.euler);
                addText(calculation_area, input, main, false);
            }
        });

        Button cubed = rootView.findViewById(R.id.cubed);
        cubed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.cubed);
                addText(calculation_area, input, main, false);
            }
        });

        Button permutation = rootView.findViewById(R.id.permutation);
        permutation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.npr);
                addText(calculation_area, input, main, false);
            }
        });

        Button combinations = rootView.findViewById(R.id.combinations);
        combinations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.ncr);
                addText(calculation_area, input, main, false);
            }
        });

        Button pi = rootView.findViewById(R.id.pi);
        pi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.pi);
                addText(calculation_area, input, main, false);
            }
        });

        Button sqrt = rootView.findViewById(R.id.sqrt);
        sqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.sqrt);
                addText(calculation_area, input, main, false);
            }
        });

        Button arc_sin = rootView.findViewById(R.id.arc_sin);
        arc_sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.arc_sin);
                addText(calculation_area, input, main, false);
            }
        });

        Button arc_cos = rootView.findViewById(R.id.arc_cos);
        arc_cos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.arc_cos);
                addText(calculation_area, input, main, false);
            }
        });

        Button arc_tan = rootView.findViewById(R.id.arc_tan);
        arc_tan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.arc_tan);
                addText(calculation_area, input, main, false);
            }
        });

        Button cubed_root = rootView.findViewById(R.id.cubed_root);
        cubed_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.cubed_root);
                addText(calculation_area, input, main, false);
            }
        });

        Button lbra = rootView.findViewById(R.id.lbra);
        lbra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.lbra);
                addText(calculation_area, input, main, false);
            }
        });

        Button rbra = rootView.findViewById(R.id.rbra);
        rbra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.rbra);
                addText(calculation_area, input, main, false);
            }
        });

        Button lbra_square = rootView.findViewById(R.id.lbra_square);
        lbra_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.lbra_square);
                addText(calculation_area, input, main, false);
            }
        });

        Button rbra_square = rootView.findViewById(R.id.rbra_square);
        rbra_square.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.rbra_square);
                addText(calculation_area, input, main, false);
            }
        });

        Button lbra_squigly = rootView.findViewById(R.id.lbra_squigly);
        lbra_squigly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.lbra_squigly);
                addText(calculation_area, input, main, false);
            }
        });

        Button rbra_squigly = rootView.findViewById(R.id.rbra_squigly);
        rbra_squigly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.rbra_squigly);
                addText(calculation_area, input, main, false);
            }
        });

        Button rand = rootView.findViewById(R.id.random_number);
        rand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.rand);
                addText(calculation_area, input, main, false);
            }
        });

        Button factorial = rootView.findViewById(R.id.factorial);
        factorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.factorial);
                addText(calculation_area, input, main, false);
            }
        });

        return rootView;
    }

}
