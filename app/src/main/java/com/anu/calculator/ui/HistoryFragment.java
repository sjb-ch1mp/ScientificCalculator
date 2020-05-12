package com.anu.calculator.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.anu.calculator.R;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class HistoryFragment extends Fragment {

    private static final String TAG = "HISTORY_TAB";
    public static HistoryFragment newInstance() {
        return new HistoryFragment();
    }
    private TextView historyText;

    /**
     * Called to have the fragment instantiate its user interface view.
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
        final View rootView = inflater.inflate(R.layout.history_fragment, container, false);

        // Set the history text area to scrollable
        TextView historyText = rootView.findViewById(R.id.history_text);
        historyText.setMovementMethod(new ScrollingMovementMethod());

        Button clear = rootView.findViewById(R.id.clear_history);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                TextView historyText = Objects.requireNonNull(getActivity()).findViewById(R.id.history_text);
                historyText.setText("");
            }
        });

        return rootView;
    }


    /**
     * Calls the super' constructor immediatly, but also instantiates the historyText variable to be
     * the created UI element for the history. This lets the addHistory method modify the element
     * with a guarantee it is initialised.
     *
     * @author: Michael Betterton (u6797866)
     * @param view The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     */
    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        historyText = (TextView) view.findViewById(R.id.history_text);
    }

    /**
     * Modifies the history text TextView area to contain the newly added expression text. It then
     * scrolls to the end of the TextView by calculating where the latest text was added to.
     *
     * @author: Michael Betterton (u6797866)
     * @param historyMessage The new message to append to the history.
     */
    void addHistory(String historyMessage){
        historyText.append(historyMessage);
        // Find the total distance we need to scroll to the bottom. This works by getting the
        // internal layout of the final line, subtracting the TextView (historyText) height, then
        // adding on the top and bottom padding to get the final distance.
        final int scrollAmount = historyText.getLayout().getLineTop(historyText.getLineCount())
                - historyText.getHeight()
                + historyText.getPaddingBottom()
                + historyText.getPaddingTop();
        // If the scroll is negative due to the text not overflowing yet, the scroll will be
        // negative and therefore there is no need to scroll.
        if (scrollAmount > 0)
            historyText.scrollTo(0, scrollAmount);
        else
            historyText.scrollTo(0, 0);
    }
}