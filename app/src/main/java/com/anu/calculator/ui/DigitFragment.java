package com.anu.calculator.ui;

import android.content.Context;
import android.os.Bundle;

import com.anu.calculator.Expression;
import com.anu.calculator.ParserException;
import com.anu.calculator.R;
import com.anu.calculator.parsers.ExpressionParser;
import com.anu.calculator.utilities.History;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import static com.anu.calculator.ui.Util.*;

public class DigitFragment extends Fragment {

    private static final String TAG = "DIGIT_TAB";

    public static DigitFragment newInstance() {
        return new DigitFragment();
    }

    private HistoryMessenger historyMessenger;


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
     *
     * @modified: Samuel Brookes (u5380100)
     *  - 07/09/2019: added try catch block for evaluate() method.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView: starting");

        final EditText calculation_area = Objects.requireNonNull(getActivity()).findViewById(R.id.calculation_textarea);
        calculation_area.setCursorVisible(true);

        final View rootView = inflater.inflate(R.layout.digit_fragment, container, false);
        final MainActivity main = (MainActivity) Objects.requireNonNull(getActivity());

        // Create a history object by loading and saving whatever is there
        History history = History.load(Objects.requireNonNull(getContext()));
        history.save(Objects.requireNonNull(getContext()));

        Button btn_dgt_0 = rootView.findViewById(R.id.dgt_0);
        btn_dgt_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_0);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_1 = rootView.findViewById(R.id.dgt_1);
        btn_dgt_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_1);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_2 = rootView.findViewById(R.id.dgt_2);
        btn_dgt_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_2);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_3 = rootView.findViewById(R.id.dgt_3);
        btn_dgt_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_3);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_4 = rootView.findViewById(R.id.dgt_4);
        btn_dgt_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_4);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_5 = rootView.findViewById(R.id.dgt_5);
        btn_dgt_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_5);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_6 = rootView.findViewById(R.id.dgt_6);
        btn_dgt_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_6);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_7 = rootView.findViewById(R.id.dgt_7);
        btn_dgt_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_7);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_8 = rootView.findViewById(R.id.dgt_8);
        btn_dgt_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_8);
                addText(calculation_area, input, main, true);
            }
        });

        Button btn_dgt_9 = rootView.findViewById(R.id.dgt_9);
        btn_dgt_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.dgt_9);
                addText(calculation_area, input, main, true);
            }
        });

        Button answer = rootView.findViewById(R.id.answer);
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.answer);
                if(main.contains("ans"))
                    addText(calculation_area, fmt(main.getDouble("ans")), main, false);
            }
        });

        Button decimal = rootView.findViewById(R.id.decimal);
        decimal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.decimal);
                addText(calculation_area, input, main, false);
            }
        });

        Button addition = rootView.findViewById(R.id.addition);
        addition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.addition);
                addText(calculation_area, input, main, false);
            }
        });

        Button subtraction = rootView.findViewById(R.id.subtraction);
        subtraction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.subtraction);
                addText(calculation_area, input, main, false);
            }
        });

        Button multiply = rootView.findViewById(R.id.multiply);
        multiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.multiply);
                addText(calculation_area, input, main, false);
            }
        });

        Button divide = rootView.findViewById(R.id.divide);
        divide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.divide);
                addText(calculation_area, input, main, false);
            }
        });

        Button percentage = rootView.findViewById(R.id.percentage);
        percentage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.percentage);
                addText(calculation_area, input, main, false);
            }
        });

        Button all_clear = rootView.findViewById(R.id.all_clear);
        all_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                resetTextArea(calculation_area);
                // Reset the eval to false as we've hit all clear.
                main.put("eval",false);
            }
        });

        Button delete = rootView.findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                int length = calculation_area.length();
                int selection_point = calculation_area.getSelectionStart();
                // If the text view calculation area has a non-zero length, enter the actual delete
                // function, otherwise just ignore the action. Also only enter the actual delete
                // function if the selection point isn't at the start. Deleting the start of the
                // string is a non-action.
                if (length > 0 && selection_point > 0) {
                    // If the cursor is not at the end of the calculation area
                    if (selection_point < length){
                        // Calculate the left hand side and right hand side of the cursor
                        String lhs = calculation_area.getText().subSequence(0,selection_point-1).toString();
                        String rhs = calculation_area.getText().subSequence(selection_point,length).toString();
                        String new_text = lhs + rhs;
                        // Set the text area to the new value
                        calculation_area.setText(new_text);
                        // If the selection point is after the end of the length, set the cursor
                        // to the end of the text view
                        if (selection_point >= length-1){
                            calculation_area.setSelection(length-2);
                        }else{
                            // If the selection point after modification is not at the end of the area,
                            // set the selection point to the original position minus 2 places.
                            calculation_area.setSelection(Math.max(selection_point-1,0));
                        }
                        // The calculation is at the end of the text area
                    }else{
                        // Drop the last character
                        calculation_area.setText(calculation_area.getText().subSequence(0, calculation_area.getSelectionStart() - 1));
                        // If the current length (original-1) is > 0, set the cursor back to the right spot
                        if(length > 1)
                            calculation_area.setSelection(calculation_area.length());
                    }
                }
            }
        });

        // Equals is a little bit more complex as it evaluates the actual expression. The basic
        // intent of the listener is:
        // 1. Parse the expression, handling any errors
        // 2. Change the Calculation Area (text) to be "=<Evaluated Expression>"
        // 3. Reset the working space.
        // 4. Add to the history fragment the latest expression.
        Button equals = rootView.findViewById(R.id.evaluate);
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Parse the expression and evaluate it
                String expression = calculation_area.getText().toString();
                ExpressionParser parser = new ExpressionParser();
                try
                {
                    History history = History.load(getContext());
                    Expression exp = parser.parse(expression, main.getBoolean("degrees"), main.getInt("precision"), history);
                    history.put(exp, main.getBoolean("degrees"));
                    history.save(getContext());

                    double evaluation = exp.evaluate();
                    // Reset the Text Area
                    resetTextArea(calculation_area);

                    // Add the text to the screen
                    calculation_area.setText(fmt(evaluation));
                    calculation_area.setSelection(calculation_area.length());

                    // Pass the history to the history fragment
                    historyMessenger.sendHistory("\n"+expression+"="+fmt(evaluation));

                    // Store the answer for future use.
                    MainActivity main = (MainActivity) Objects.requireNonNull(getActivity());
                    main.put("ans",evaluation);
                    Log.d(TAG,"stored answer: '"+main.getDouble("ans")+"'");

                    // Store that a evaluation has taken place
                    main.put("eval", true);
                }
                catch(ParserException e)
                {
                    calculation_area.setText(e.getErrorMessage());
                    calculation_area.setSelection(calculation_area.length());
                    historyMessenger.sendHistory("\n"+expression);
                    historyMessenger.sendHistory("\n"+e.getErrorMessage());
                }
            }
        });
        Log.d(TAG,"onCreateView: complete");
        return rootView;
    }

    /**
     * Checks to see if the MainActivity implements the HistoryMessenger Interface. This is a
     * requirement so that this fragment can append messages (historical expressions) to the history
     * when expressions are evaluated. If the MainActivity doesn't implement the interface, a
     * exception is thrown.
     *
     * @author: Michael Betterton (u6797866)
     * @param context The applications working context
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            historyMessenger = (HistoryMessenger) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}
