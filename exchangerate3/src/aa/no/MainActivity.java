package aa.no;

import android.app.Activity;
import android.widget.AdapterView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.GridView;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity
        implements AdapterView.OnItemSelectedListener {
    /* This part set up a LinearLayout with a TextView and a GridView.
     * Code is used to fill the GridView with buttons.
     */
    private static final String[] items = {"USA", "Mexico", "Canada"};
    private static final String[] itemserviceSpinner = {"Mat", "Taxi", "Baggasje(Per bag)"};
    private static final String[] items3 = {"Normal", "Meget bra", "Eksepsjonell"};
    private static final String[] items4 = {"Normal", "Bra"};
    private static final String[] items5 = {"Normal", "Mye"};
    GridView mKeypadGrid;
    TextView userInputText;
    String numbers = "0";
    TextView tipsText;
    ArrayAdapter<String> ac;

    // THIS HAS TO DO WITH HANDLING SPINNER EVENTS
    Spinner countrySpinner;
    int p = 0;
    //Extra spinner for selecting service
    Spinner serviceSpinner;
    int p2 = 0;
    Spinner qualitySpinner;
    int p3 = 0;
    KeypadAdapter mKeypadAdapter;

    public void onItemSelected(AdapterView<?> parent,
                               View v, int position, long id) {

        //Check which of the spinners has been selected
        System.out.println(parent.getId());
        switch (parent.getId()) {
            case R.id.spinner:
                p = position;
                break;
            case R.id.spinner2:
                p2 = position;
                switch (position) {
                    case 0:
                        ac = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_item, items3);
                        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        qualitySpinner.setAdapter(ac);
                        break;
                    case 1:
                        ac = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_item, items4);
                        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        qualitySpinner.setAdapter(ac);
                        break;
                    case 2:
                        ac = new ArrayAdapter<>(this,
                                android.R.layout.simple_spinner_item, items5);
                        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        qualitySpinner.setAdapter(ac);
                        break;
                }
                break;
            case R.id.spinner3:
                p3 = position;
                break;
        }
    }

    ;

    public void onNothingSelected(AdapterView<?> parent) {
        switch (parent.getId()) {
            case R.id.spinner:
                p = 0;
                break;
            case R.id.spinner2:
                p2 = 0;
                break;
            case R.id.spinner3:
                p3 = 0;
                break;
        }

    }


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        // Fill spinner with exchange rates
        countrySpinner = (Spinner) findViewById(R.id.spinner);
        countrySpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> aa = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(aa);
        //Setting up the second spinner
        serviceSpinner = (Spinner) findViewById(R.id.spinner2);
        serviceSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> ab = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, itemserviceSpinner);
        ab.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        serviceSpinner.setAdapter(ab);

        //setting up third spinner for servicelevel
        qualitySpinner = (Spinner) findViewById(R.id.spinner3);
        qualitySpinner.setOnItemSelectedListener(this);
        ac = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, items3);
        ac.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qualitySpinner.setAdapter(ac);
        // End of code to fill spinner
        // Get reference to the GridView and TextView
        mKeypadGrid = (GridView) findViewById(R.id.grdButtons);
        userInputText = (TextView) findViewById(R.id.txtInput);
        tipsText = (TextView) findViewById(R.id.tips);
        // Create Keypad Adapter
        mKeypadAdapter = new KeypadAdapter(this);
        // Set adapter of the keypad grid
        mKeypadGrid.setAdapter(mKeypadAdapter);
        // Set button click listener of the keypad adapter
        // Process button clicks
        // Several buttons needs special treatment
        //Add format
        mKeypadAdapter.setOnButtonClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Button btn = (Button) v;
                        // Get the KeypadButton value which is used to identify the
                        // keypad button from the Button'countrySpinner tag
                        KeypadButton keypadButton = (KeypadButton) btn.getTag();
                        // Process keypad button
                        if (keypadButton.getText().toString() == "<-") {
                            numbers = numbers.substring(0, numbers.length() - 1);
                            if (numbers.length() == 0) {
                                numbers = "0";
                            }
                            userInputText.setText(numbers);

                        } else if (keypadButton.getText().toString() == "C") {
                            numbers = "0";
                            userInputText.setText(numbers);
                            tipsText.setText(R.string.tips);
                        } else if (keypadButton.getText().toString() != "Beregn") {
                            //userInputText.append(keypadButton.getText().toString());
                            if (numbers != "0") {
                                numbers = numbers + keypadButton.getText().toString();
                            } else {
                                numbers = keypadButton.getText().toString();
                            }
                            userInputText.setText(numbers);
                        } else {
                            double l = Double.parseDouble(numbers);
                            double o = 0;
                            String currency = "";
                            //decide currency from Country spinner
                            switch (p) {
                                case 0:
                                    currency = "$";
                                    break;
                                case 1:
                                    currency = "Peso(s)";
                                    break;
                                case 2:
                                    currency = "C$";
                                    break;
                            }

                            //switch on service type
                            //inner switches on countries
                            switch (p2) {
                                case 0:
                                    switch (p3) {
                                        case 0:
                                            System.out.println(0);
                                            switch (p) {
                                                case 0:
                                                    o = (l / 100) * 10;
                                                    break;
                                                case 1:
                                                    o = (l / 100) * 10;
                                                    break;
                                                case 2:
                                                    o = (l / 100) * 15;
                                                    break;
                                            }
                                            break;
                                        case 1:
                                            System.out.println(1);
                                            switch (p) {
                                                case 0:
                                                    o = (l / 100) * 12.5;
                                                    break;
                                                case 1:
                                                    o = (l / 100) * 12.5;
                                                    break;
                                                case 2:
                                                    o = (l / 100) * 17.5;
                                                    break;
                                            }
                                            break;
                                        case 2:
                                            switch (p) {
                                                case 0:
                                                    o = (l / 100) * 15;
                                                    break;
                                                case 1:
                                                    o = (l / 100) * 15;
                                                    break;
                                                case 2:
                                                    o = (l / 100) * 20;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                                case 1:
                                    switch (p3) {
                                        case 0:
                                            switch (p) {
                                                case 0:
                                                    o = (l / 100) * 10;
                                                    break;
                                                case 1:
                                                    o = 0;
                                                    break;
                                                case 2:
                                                    o = (l / 100) * 10;
                                                    break;
                                            }
                                            break;
                                        case 1:
                                            switch (p) {
                                                case 0:
                                                    o = (l / 100) * 10;
                                                    break;
                                                case 1:
                                                    o = 60;
                                                    break;
                                                case 2:
                                                    o = (l / 100) * 13;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                                case 2:
                                    switch (p3) {
                                        case 0:
                                            switch (p) {
                                                case 0:
                                                    o = 1;
                                                    break;
                                                case 1:
                                                    o = 10;
                                                    break;
                                                case 2:
                                                    o = 1;
                                                    break;
                                            }
                                            break;
                                        case 1:
                                            switch (p) {
                                                case 0:
                                                    o = 2;
                                                    break;
                                                case 1:
                                                    o = 20;
                                                    break;
                                                case 2:
                                                    o = 2;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }


                            //numbers = String.valueOf(o);
                            tipsText.setText(String.format("%.2f " + currency, o));
                        }
                    }
                }

        );
    }
}

