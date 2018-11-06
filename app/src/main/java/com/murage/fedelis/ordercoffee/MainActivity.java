package com.murage.fedelis.ordercoffee;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String name="";
    EditText numberOfCups;
    TextView txtUserSalutation, txtTitle, billAmount, txtInfo;
    Button checkOutButton, btnChangeOrder;
    ProgressBar progressBar;
    final double RATE = 2.5;
    int amountOfSugar = 2;
    static int order= 0;
    static double bill=0;
    String Temperature = "Steaming hot", CoffeeType = "Cappuccino";

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
        btnChangeOrder = findViewById(R.id.btnChangeOrder);
        askName();

        progressBar.setVisibility(View.GONE);
        txtInfo.setText("Your order: "+Temperature+" "+CoffeeType+"\n Sugar: "+amountOfSugar+" teaspoons.");
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
                int tempOrder = 0;
                double tempBill =0;
                if (s.toString() == "") {
                    billAmount.setText("Bill amount \n $0.00");
                } else {
                    double billAmount = (Double.parseDouble((s.toString() + 0)) * RATE / 10);
                    MainActivity.this.billAmount.setText("Bill amount \n $" + billAmount);
                    tempOrder = (Integer.parseInt(s.toString() + 0) / 10);
                    tempBill = (Double.parseDouble((s.toString() + 0)) * RATE / 10);
                }
                if (tempOrder > 0) {
                    order = (Integer.parseInt(s.toString() + 0)) / 10;
                }
                if (tempBill > 0) {
                    bill = (Double.parseDouble((s.toString() + 0)) * RATE / 10);
                }
                _ignore = false;
            }
        });
        btnChangeOrder.setOnClickListener(new View.OnClickListener() {
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
        checkOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(numberOfCups.getText().toString()+"").equals("")) {
                    numberOfCups.setText("");
                    checkOut();
                }else{
                    Toast.makeText(MainActivity.this, "Please input how many cups of coffee you'd like.", Toast.LENGTH_SHORT).show();
                    numberOfCups.requestFocus();
                }

            }
        });
    }
    void changeValue(){
        txtInfo.setText("Your order: "+Temperature+" "+CoffeeType+"\n Sugar: "+amountOfSugar+" teaspoons.");
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
    void checkOut(){
        progressBar.setVisibility(View.VISIBLE);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setMessage("Thank you for your order. \n "+order+((order==1)?" cup":" cups")+" of "
                +Temperature+" "+CoffeeType+" will be delivered to you within the next 15 minutes.\nBill amount: $"+bill+"\nStay put.  Remember, we always care for you.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        order=0;
                        bill=0;
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        progressBar.setVisibility(View.GONE);
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
    void askName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        final EditText nameGetter = new EditText(this);
        nameGetter.setHint("Enter name");
        nameGetter.setLeft(15);
        nameGetter.setRight(15);
        nameGetter.setTop(10);
        nameGetter.setBottom(10);
        nameGetter.setPadding(20,20,20,20);
        builder.setView(nameGetter);
        builder.setMessage("Can you please tell us your name?")
                .setCancelable(false)
                .setPositiveButton("That's my name", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        name = nameGetter.getText().toString();
                        txtUserSalutation.setText("Hello "+name);
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}