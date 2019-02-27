package com.example.tipcalculator_project_miles;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    EditText amount;
    EditText numPeople;
    EditText radioButtonOtherEdit;
    Button calculate;
    Button reset;
    TextView totalTip;
    TextView tipPerPerson;
    RadioGroup radioGroup;
    RadioButton radioButtonOther;
    RadioButton radioButton10;
    RadioButton radioButton15;
    RadioButton radioButton20;
    RadioButton radioButton25;

    boolean amountValid = false;
    boolean numPeopleValid = false;
    boolean radioButtonOtherEditValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        numPeople = findViewById(R.id.numPeople);
        radioButtonOtherEdit = findViewById(R.id.radioButtonOtherEdit);

        calculate = findViewById(R.id.calculate);
        reset = findViewById(R.id.reset);

        totalTip = findViewById(R.id.totalTip);
        tipPerPerson = findViewById(R.id.tipPerPerson);

        radioGroup= findViewById(R.id.radioGroup);
        radioButton10 = findViewById(R.id.radioButton10);
        radioButton15 = findViewById(R.id.radioButton15);
        radioButton20 = findViewById(R.id.radioButton20);
        radioButton25 = findViewById(R.id.radioButton25);
        radioButtonOther = findViewById(R.id.radioButtonOther);


        radioButtonOtherEdit.setEnabled(false);
        calculate.setEnabled(false);

        amount.setOnKeyListener(mKeyListener);
        numPeople.setOnKeyListener(mKeyListener);
        radioButtonOtherEdit.setOnKeyListener(mKeyListener);

        radioButton10.setOnClickListener(onLickListener);
        radioButton15.setOnClickListener(onLickListener);
        radioButton20.setOnClickListener(onLickListener);
        radioButton25.setOnClickListener(onLickListener);

        radioButtonOther.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(radioButtonOtherEdit.getText().toString().equals("")) {
                    calculate.setEnabled(false);
                }
                radioButtonOtherEdit.setEnabled(true);
                radioButtonOtherEditValid = false;
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                double prcnt_amt;
                int amt = Integer.parseInt(amount.getText().toString());
                int ppl = Integer.parseInt(numPeople.getText().toString());
                int selectedID = radioGroup.getCheckedRadioButtonId();

                if(selectedID == R.id.radioButtonOther){
                    prcnt_amt = Integer.parseInt(radioButtonOtherEdit.getText().toString());
                }
                else {
                    RadioButton selectedButton = findViewById(selectedID);
                    prcnt_amt = Integer.parseInt(selectedButton.getText().toString());
                }

                double tipTot = amt * (prcnt_amt/100);
                double tipPerPrsn = tipTot/ppl;

                totalTip.setText(String.format(getString(R.string.total_tip_format), tipTot));
                tipPerPerson.setText(String.format(getString(R.string.tip_per_person_format), tipPerPrsn));
            }
        });

        reset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                amount.setText("");
                numPeople.setText("");
                radioButtonOtherEdit.setText("");

                radioGroup.clearCheck();

                calculate.setEnabled(false);
                radioButtonOtherEdit.setEnabled(false);

                amountValid = false;
                numPeopleValid = false;
                radioButtonOtherEditValid = false;

                totalTip.setText(getResources().getString(R.string.total_tip));
                tipPerPerson.setText(getResources().getString(R.string.tip_per_person));
            }
        });
    }

    private View.OnKeyListener mKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if(keyCode == KeyEvent.KEYCODE_ENTER) {
                switch (v.getId()) {
                    case R.id.amount:
                        try {
                            if (!(amount.getText().toString().equals("")) && Integer.parseInt(amount.getText().toString()) > 1) {

                                amountValid = true;

                                if (numPeopleValid && radioButtonOther.isChecked() && radioButtonOtherEditValid) {
                                    calculate.setEnabled(true);
                                } else if (numPeopleValid && radioGroup.getCheckedRadioButtonId() != -1) {
                                    calculate.setEnabled(true);
                                }
                            } else {
                                amountValid = false;
                            }
                        } catch (NumberFormatException e) {
                            showErrorAlert("Please enter a valid amount", R.id.amount);
                        }

                    case R.id.numPeople:
                        try {
                            if (!(numPeople.getText().toString().equals("")) && Integer.parseInt(numPeople.getText().toString()) >= 1) {
                                System.out.println(numPeople.getText().toString());
                                numPeopleValid = true;

                                if (amountValid && radioButtonOther.isChecked() && radioButtonOtherEditValid) {
                                    calculate.setEnabled(true);
                                } else if (amountValid && radioGroup.getCheckedRadioButtonId() != -1) {
                                    calculate.setEnabled(true);
                                }
                            } else {
                                numPeopleValid = false;
                            }
                        } catch (NumberFormatException e) {
                            showErrorAlert("Please enter a valid number of people", R.id.numPeople);
                        }


                    case R.id.radioButtonOtherEdit:
                        try {
//                            if(Integer.parseInt(radioButtonOtherEdit.getText().toString()) > 100){
//                                throw new KeyException();
//                            }
                            if (!(radioButtonOtherEdit.getText().toString().equals("")) && Integer.parseInt(radioButtonOtherEdit.getText().toString()) >= 1 ) {

                                radioButtonOtherEditValid = true;

                                if (amountValid && numPeopleValid) {
                                    calculate.setEnabled(true);
                                }
                            } else {
                                radioButtonOtherEditValid = false;
                            }
                        } catch (NumberFormatException e) {
                            showErrorAlert("Please enter an amount between 1 and 100", R.id.radioButtonOtherEdit);
                        }
//                        catch (KeyException e){
//                            showErrorAlert("Please enter an amount between 1 and 100000", R.id.radioButtonOtherEdit);
//                        }
                }
            }
            return false;
        }

    };

    private View.OnClickListener onLickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.radioButton10:
                        if(amountValid && numPeopleValid){
                            calculate.setEnabled(true);
                        }
                        radioButtonOtherEdit.setEnabled(false);

                    case R.id.radioButton15:
                        if(amountValid && numPeopleValid){
                            calculate.setEnabled(true);
                        }
                        radioButtonOtherEdit.setEnabled(false);

                    case R.id.radioButton20:
                        if(amountValid && numPeopleValid){
                            calculate.setEnabled(true);
                        }
                        radioButtonOtherEdit.setEnabled(false);

                    case R.id.radioButton25:
                        if(amountValid && numPeopleValid){
                            calculate.setEnabled(true);
                        }
                        radioButtonOtherEdit.setEnabled(false);
                }
            }
        };

    private void showErrorAlert(String errorMessage, final int fieldId) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(errorMessage)
                .setNeutralButton("Close",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                findViewById(fieldId).requestFocus();
                            }
                        }).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String totTip = totalTip.getText().toString();
        String tipPerPer = tipPerPerson.getText().toString();

        outState.putBoolean("calculateValue", calculate.isEnabled());
        outState.putBoolean("radioButtonOtherEditValue", radioButtonOtherEdit.isEnabled());
        outState.putString("totalTip", totTip);
        outState.putString("tipPerPerson", tipPerPer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);

        String totTip = bundle.getString("totalTip");
        totalTip.setText(totTip);

        String tipPerPer = bundle.getString("tipPerPerson");
        tipPerPerson.setText(tipPerPer);
        radioButtonOtherEdit.setEnabled(bundle.getBoolean("radioButtonOtherEditValue"));
        calculate.setEnabled(bundle.getBoolean("calculateValue"));
    }
}
