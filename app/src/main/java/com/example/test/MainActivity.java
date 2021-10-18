package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.motion.widget.MotionLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.test.custom_views.Calculator;

public class MainActivity extends AppCompatActivity {

    Calculator calc = new Calculator();

    TextView inputTv;
    TextView historyTv;
    ImageButton removeIb;
    MotionLayout mainSwitch;
    ImageButton sunIv, moonIv;
    boolean isLight = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isLight) {
            mainSwitch.setTransition(R.id.switch_to_dark);
            mainSwitch.setProgress(1f);
        }

        findViews();
        inputTv.setText("0");
        historyTv.setText("");

        /* Remove Button */
        removeIb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!calc.getNewOperation()) {
                    calc.setNewOperation(true);
                    calc.setOldNumber(0.0);
                    calc.setHistory("");
                    inputTv.setText("0");
                    historyTv.setText("");
                    calc.setEqual();

                } else {
                    int len = inputTv.getText().toString().length();
                    if (len == 1 && inputTv.getText().toString().equals("0")) {}
                    else if (len == 1) {
                        inputTv.setText("0");
                    }
                    else if (len > 0) {
                        inputTv.setText(inputTv.getText().toString().substring(0, len - 1));
                    }
                }
            }
        });

        /* To change the mode */
        mainSwitchOnClick();
    }

    /* Saves state */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("light", isLight);
        savedInstanceState.putString("digits", inputTv.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        isLight = savedInstanceState.getBoolean("light");
        inputTv.setText(savedInstanceState.getString("digits"));

        if (isLight) {
            mainSwitch.setTransition(R.id.switch_to_light);
            mainSwitch.setProgress(1f);

        } else {
            mainSwitch.setTransition(R.id.switch_to_dark);
            mainSwitch.setProgress(1f);
        }
    }


    /* OnClick function for keyboard's numbers 0-9 */
    public void numOnClick(View view) {
        TextView current = (TextView) view;
        String number = inputTv.getText().toString();
        String newNumber = "";

        if (number.equals("0") && !current.getText().toString().equals("0")) {
            newNumber = current.getText().toString();

        } else if (current.getText().toString().contains(".") && number.contains(".")) {
            newNumber = number;

        } else if (!number.equals("0")){
            newNumber = number + current.getText().toString();

        }

        // Show on inputTv
        inputTv.setText(newNumber);

        // Set number for calculator
        if (!newNumber.equals(".")) {
            calc.setOldNumber(Double.parseDouble(newNumber));
        }
    }


    /* OnClick function for operator buttons */
    public void operatorOnClick(View view) {
        // For avoid duplicated equal button
        calc.setEqual();

        if (!calc.getNewOperation()) {
            calc.setHistory("");
        }

        // Free inputTv
        inputTv.setText("0");

        // Set operator
        switch (view.getId()) {
            case R.id.plus_button:
                calc.setHistory(calc.newHistory() + " + ");
                break;

            case R.id.minus_button:
                calc.setHistory(calc.newHistory() + " - ");
                break;

            case R.id.multiply_button:
                calc.setHistory(calc.newHistory() + " * ");
                break;

            case R.id.divide_button:
                calc.setHistory(calc.newHistory() + " / ");
        }

        System.out.println(calc.getHistory());
        // Show history
        historyTv.setText(calc.getHistory());
    }


    /* OnClick function for equal button */
    public void equalOnClick(View view) {
        // Set newOperation to false
        calc.setNewOperation(false);
        calc.addEqual();

        // Set new history
        calc.setHistory(calc.newHistory());

        // Get expr to calculate
        String expr = calc.getHistory();

        // Set new history
        calc.setHistory(expr + " = ");

        // Show new history
        if (calc.getEqual() == 1) {
            historyTv.setText(calc.getHistory());
        }

        // Calculate
        double result = calc.calculate(expr);

        // Set result as oldNumber
        calc.setOldNumber(result);

        // Show result
        inputTv.setText(Double.toString(result));
    }


    public void mainSwitchOnClick() {
        sunIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLight = true;
                mainSwitch.setTransition(R.id.switch_to_light);
                mainSwitch.transitionToEnd();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }, 100);
            }
        });

        moonIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLight = false;
                mainSwitch.setTransition(R.id.switch_to_dark);
                mainSwitch.transitionToEnd();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                }, 100);
            }
        });
    }


    /* Finds views */
    public void findViews() {
        inputTv = findViewById(R.id.main_input_tv);
        historyTv = findViewById(R.id.history_tv);
        removeIb = findViewById(R.id.main_remove_ib);
        mainSwitch = findViewById(R.id.layout_switch);
        sunIv = findViewById(R.id.switch_right_image);
        moonIv = findViewById(R.id.switch_left_image);
    }
}