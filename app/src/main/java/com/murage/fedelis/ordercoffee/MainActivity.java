package com.murage.fedelis.ordercoffee;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText numberOfCups;
    TextView txtUserSalutation, txtTitle, billAmount, txtInfo;
    Button checkOutButton;
    ProgressBar progressBar;
    final double RATE = 2.5;
    int amountOfSugar = 2;
    String Temperature = "Steaming hot", CoffeeType = "Capuccino";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        numberOfCups = findViewById(R.id.numberOfCups);
        txtTitle = findViewById(R.id.txtTitle);
        txtUserSalutation = findViewById(R.id.txtUserSalutation);
        billAmount = findViewById(R.id.orderAmount);
        checkOutButton = findViewById(R.id.checkoutButton);
        progressBar = findViewById(R.id.progressBar);
        txtInfo = findViewById(R.id.txtInfo);

        progressBar.setVisibility(View.GONE);
        txtInfo.setText(Temperature+" "+CoffeeType+"\n Sugar: "+amountOfSugar+" teaspoons. \t "+"(Click to change)");
        numberOfCups.addTextChangedListener(new TextWatcher() {
            boolean _ignore = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (_ignore)
                    return;
                _ignore = true;
                if ((s.toString() == "") || (s.toString() == null)){
                    billAmount.setText("Bill amount \n $0.00");
                }else{
                    double billAmount = (Double.parseDouble((s.toString()+0))*RATE/10);
                    MainActivity.this.billAmount.setText("Bill amount \n $"+ billAmount);
                }
                _ignore = false;
            }
        });
        txtInfo.setClickable(true);
        txtInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogBox();
            }
        });
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    void changeValue(){
        txtInfo.setText("Your order: "+Temperature+" "+CoffeeType+"\n Sugar: "+amountOfSugar+" teaspoons. \t "+"(Click to change)");
    }
    void DialogBox(){
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_box);
        dialog.setTitle("Choose your preferences");
        final TextView txtInfo = dialog.findViewById(R.id.txtInfo);
        final EditText numberOfTeaspoons = dialog.findViewById(R.id.numberOfTeaspoons);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        Button btnOK = dialog.findViewById(R.id.btnOK);
        final Spinner CoffeeChoices = dialog.findViewById(R.id.CoffeeChoices);
        final Spinner TemperatureChoice = dialog.findViewById(R.id.TemperatureChoice);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temperature = "Steaming hot";
                CoffeeType = "Cappuccino";
                amountOfSugar = 2;
                dialog.cancel();
            }
        });
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Temperature = TemperatureChoice.getSelectedItem().toString();
                CoffeeType = CoffeeChoices.getSelectedItem().toString();
                amountOfSugar = Integer.parseInt(numberOfTeaspoons.getText().toString()+"0")/10;
                changeValue();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(this);
        menuInflater.inflate(R.menu.main_menu,menu);
        return  true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.exit:
                this.onBackPressed();
                break;
            case R.id.CoffeeOptions:
                DialogBox();
                break;
        }
        return true;

    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Do you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}