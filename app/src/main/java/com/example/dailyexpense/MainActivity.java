package com.example.dailyexpense;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText etAmount, etDescription;
    Spinner spCategory;
    Button btnAdd, btnCall, btnSMS, btnEmail;
    ListView listView;

    ArrayList<String> expenseList;
    ArrayAdapter<String> listAdapter;

    String[] categories = {"Office", "Groceries", "Travel", "Food", "Other"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etAmount = findViewById(R.id.etAmount);
        etDescription = findViewById(R.id.etDescription);
        spCategory = findViewById(R.id.spCategory);
        btnAdd = findViewById(R.id.btnAdd);
        btnCall = findViewById(R.id.btnCall);
        btnSMS = findViewById(R.id.btnSMS);
        btnEmail = findViewById(R.id.btnEmail);
        listView = findViewById(R.id.listView);

        // Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                categories);
        spCategory.setAdapter(spinnerAdapter);

        // List
        expenseList = new ArrayList<>();
        expenseList.add("Office Supplies - $150");
        expenseList.add("Groceries - $70");
        expenseList.add("Taxi Ride - $25");

        listAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                expenseList);
        listView.setAdapter(listAdapter);

        // Add Expense
        btnAdd.setOnClickListener(v -> {
            String amount = etAmount.getText().toString();
            String description = etDescription.getText().toString();

            if (!amount.isEmpty() && !description.isEmpty()) {
                String expense = description + " - $" + amount;
                expenseList.add(expense);
                listAdapter.notifyDataSetChanged();
                etAmount.setText("");
                etDescription.setText("");
            }
        });

        // Call Accountant with Confirmation
        btnCall.setOnClickListener(v -> {
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Confirm Call")
                    .setMessage("Do you want to call the accountant?")
                    .setNegativeButton("Cancel", null)
                    .setPositiveButton("Call", (dialog, which) -> {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(android.net.Uri.parse("tel:9876543210"));
                        startActivity(intent);
                    })
                    .show();
        });

        // Send SMS
        btnSMS.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(android.net.Uri.parse("sms:9876543210"));
            intent.putExtra("sms_body", "Expense details attached.");
            startActivity(intent);
        });

        // Send Email
        btnEmail.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            intent.putExtra(Intent.EXTRA_EMAIL,
                    new String[]{"accountant@gmail.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Expense Report");
            intent.putExtra(Intent.EXTRA_TEXT, "Please find the expense details.");
            startActivity(Intent.createChooser(intent, "Send Email"));
        });
    }
}