package com.example.admir.ourproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
    EditText length;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        length = (EditText) findViewById(R.id.passwordLength);
        length.setGravity(Gravity.CENTER);

        length.getBackground().setAlpha(50);
    }

    public void textClick(View v) {
        Toast.makeText(this, "Här kommer det att gå att välja key genom att skriva in vad man vill!", Toast.LENGTH_LONG).show();
    }

    public void imageClick(View v) {
        Toast.makeText(this, "Här kommer det komma upp bilder på FB, Instagram, Twitter, Pornhub.", Toast.LENGTH_LONG).show();

    }

    public void getPassword(View v) {
        Toast.makeText(this, "Här får man upp den krypterade lösenordet!", Toast.LENGTH_LONG).show();

    }
}
