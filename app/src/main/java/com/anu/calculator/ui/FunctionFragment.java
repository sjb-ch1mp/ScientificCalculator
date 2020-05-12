package com.anu.calculator.ui;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.anu.calculator.R;

import java.util.Objects;

import static com.anu.calculator.ui.Util.addText;

/**
 * The fragment for storing and saving functions.
 */
public class FunctionFragment extends Fragment {

    private static final String TAG = "FUNCTION_TAB";

    public static FunctionFragment newInstance() {
        return new FunctionFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG,"onCreateView: starting");

        final EditText calculation_area = Objects.requireNonNull(getActivity()).findViewById(R.id.calculation_textarea);
        final MainActivity main = (MainActivity) Objects.requireNonNull(getActivity());

        calculation_area.setCursorVisible(true);

        final View rootView = inflater.inflate(R.layout.function_fragment, container, false);

        Button btn_a = rootView.findViewById(R.id.btn_a);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.a);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_b = rootView.findViewById(R.id.btn_b);
        btn_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.b);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_c = rootView.findViewById(R.id.btn_c);
        btn_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.c);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_d = rootView.findViewById(R.id.btn_d);
        btn_d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.d);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_f = rootView.findViewById(R.id.btn_f);
        btn_f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.f);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_g = rootView.findViewById(R.id.btn_g);
        btn_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.g);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_h = rootView.findViewById(R.id.btn_h);
        btn_h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.h);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_i = rootView.findViewById(R.id.btn_i);
        btn_i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.i);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_j = rootView.findViewById(R.id.btn_j);
        btn_j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.j);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_k = rootView.findViewById(R.id.btn_k);
        btn_k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.k);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_m = rootView.findViewById(R.id.btn_m);
        btn_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.m);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_n = rootView.findViewById(R.id.btn_n);
        btn_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.n);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_p = rootView.findViewById(R.id.btn_p);
        btn_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.p);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_q = rootView.findViewById(R.id.btn_q);
        btn_q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.q);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_r = rootView.findViewById(R.id.btn_r);
        btn_r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.r);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_s = rootView.findViewById(R.id.btn_s);
        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.s);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_t = rootView.findViewById(R.id.btn_t);
        btn_t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.t);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_u = rootView.findViewById(R.id.btn_u);
        btn_u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.u);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_v = rootView.findViewById(R.id.btn_v);
        btn_v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.v);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_equals = rootView.findViewById(R.id.equals);
        btn_equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.equals);
                addText(calculation_area, input, main, false);
            }
        });


        Button btn_alpha = rootView.findViewById(R.id.alpha);
        btn_alpha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.alpha);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_beta = rootView.findViewById(R.id.beta);
        btn_beta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.beta);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_delta = rootView.findViewById(R.id.delta);
        btn_delta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.delta);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_gamma = rootView.findViewById(R.id.gamma);
        btn_gamma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.gamma);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_w = rootView.findViewById(R.id.btn_w);
        btn_w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.w);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_x = rootView.findViewById(R.id.btn_x);
        btn_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.x);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_y = rootView.findViewById(R.id.btn_y);
        btn_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.y);
                addText(calculation_area, input, main, false);
            }
        });

        Button btn_z = rootView.findViewById(R.id.btn_z);
        btn_z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String input = getString(R.string.z);
                addText(calculation_area, input, main, false);
            }
        });

        Log.d(TAG,"onCreateView: finishing");
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
    }


}
