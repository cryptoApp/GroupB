package com.example.admir.ourproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import CustomAlgorithm.Main;


public class MainActivity extends Activity {
    EditText length;
    Main chryptoAlgorithm;
    String imgKey;
    Button encPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        length = (EditText) findViewById(R.id.passwordLength);
        length.setGravity(Gravity.CENTER);
        length.getBackground().setAlpha(50);
        imgKey = "nokey";
        encPass = (Button) findViewById(R.id.encryptedPassword);
    }

    public void textClick(View v) {
       openDialogForInput();
    }


    public void imageClick(View v) {
        openDialogForImageKey();
    }

    public void getPassword(View v) {
        Toast.makeText(this, "Här får man upp den krypterade lösenordet!", Toast.LENGTH_LONG).show();

    }

    private void openDialogForInput() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View v = factory.inflate(R.layout.alertwithtwoedits, null);
        final EditText key = (EditText) v.findViewById(R.id.editText);
        final EditText pass = (EditText) v.findViewById(R.id.editText2);
        key.setHint("Write your key");
        pass.setHint("Write your password");
        alert.setView(v);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String keyString = key.getText().toString();
                String passString = pass.getText().toString();
                try {
                    chryptoAlgorithm = new Main(keyString, passString,MainActivity.this);

                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Error occured.." + e,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        alert.show();

    }

    private void openDialogForImageKey() {

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View v = factory.inflate(R.layout.imagekeydialog, null);
        final EditText pass = (EditText) v.findViewById(R.id.password);
        final TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(imgKey);
        ImageView fb = (ImageView) v.findViewById(R.id.facebook);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey = "Facebook";
                tv.setText(imgKey);
            }
        });

        ImageView ig = (ImageView) v.findViewById(R.id.instagram);
        ImageView bad = (ImageView) v.findViewById(R.id.badoo);
        ImageView woff = (ImageView) v.findViewById(R.id.woff);
        ImageView tind = (ImageView) v.findViewById(R.id.tinder);
        ImageView twitt = (ImageView) v.findViewById(R.id.twitter);
        pass.setHint("Write your password");
        alert.setView(v);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String passString = pass.getText().toString();
                try {
                    chryptoAlgorithm = new Main(imgKey, passString,MainActivity.this);
                }catch (Exception e) {
                    Toast.makeText(getApplicationContext(),"Error occured.." + e,Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });

        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.cancel();
                    }
                });
        alert.show();

    }
}
