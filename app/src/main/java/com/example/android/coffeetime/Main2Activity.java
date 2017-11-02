package com.example.android.coffeetime;

import android.support.v7.app.AppCompatActivity;
import android.annotation.TargetApi;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.icu.text.StringPrepParseException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.coffeetime.R;

import static android.R.attr.order;
import static android.R.attr.start;
import static android.R.id.message;

/**
 * This app displays an order form to order coffee.
 */
public class Main2Activity extends AppCompatActivity {

    public int quantity = 1;
    String message;
    Button print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        print=(Button)findViewById(R.id.printid);
        print.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"));

                i.putExtra(Intent.EXTRA_TEXT,message);
                i.putExtra(Intent.EXTRA_SUBJECT,"bill generated");
                String[]to={"jainsunidhi31@gmail.com","nidhiverma1997@gmailcom"};
                i.putExtra(Intent.EXTRA_EMAIL,to);
                if (i.resolveActivity(getPackageManager()) != null) {
                    startActivity(i);
                }
            }
        });
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        int price;
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        if(hasWhippedCream && hasChocolate)
            price = calculatePrice(100);
        else if(hasChocolate)
            price = calculatePrice(60);
        else if(hasWhippedCream)
            price = calculatePrice(40);
        else
            price = calculatePrice(0);

        message = createOrderSummary(price, hasWhippedCream);
        displayMessage(message);
    }


    public void increment(View view) {
        quantity++;
        display(quantity);
    }

    public void decrement(View view) {
        quantity--;
        if(quantity < 1) {
            quantity = 1;
        }
        display(quantity);
    }


    /**
     * This method displays the given quantity value on the screen.
     */
    public void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
    public String createOrderSummary(int price, boolean toppings){
        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();
        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();
        String orderSummary = "Name : Customer";
        orderSummary += "\nQuantity : "+ quantity;
        if(hasWhippedCream)
            orderSummary += "\nWhipped Cream: ₹"+ 40*quantity ;
        if(hasChocolate)
            orderSummary += "\nChocolate: ₹"+60*quantity;
        orderSummary += "\nPlain Coffee : ₹"+75*quantity;
        orderSummary += "\nGrand Total : "+price;
        orderSummary += "\nThank You!";
        return orderSummary;
    }

    /**
     * This method will return the order price
     */
    public int calculatePrice(int toppingsPrice){

        return quantity*(75 + toppingsPrice);
    }
    public void displayMessage(String message ) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}
