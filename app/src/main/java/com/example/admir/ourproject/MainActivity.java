package com.example.admir.ourproject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import CustomAlgorithm.StartAlgorithm;


public class MainActivity extends Activity {
    EditText length, finalPass;
    StartAlgorithm chryptoAlgorithm;
    String imgKey;

    SeekBar seekbar;
    int reqLength;

    boolean specialChar = false;
    boolean upperCase = false;
    boolean lowerCase = false;
    boolean useNumber = false;
    boolean isTouched = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        length = (EditText) findViewById(R.id.passwordLength);
        length.setGravity(Gravity.CENTER);
        length.getBackground().setAlpha(50);
        length.setEnabled(false);
        seekbar = (SeekBar) findViewById(R.id.seekBar);

        finalPass = (EditText) findViewById(R.id.finalPass);
        finalPass.setGravity(Gravity.CENTER);
        finalPass.getBackground().setAlpha(50);

        imgKey = "nokey";


        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                length.setText(Integer.toString(progress + 5));
            }

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    public void textClick(View v) {
        openDialogForInput();
    }


    public void imageClick(View v) {
        openDialogForImageKey();
    }

    public void infoClick(View view) {
        startActivity(new Intent(MainActivity.this, InfoActivity.class));
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()) {
            case R.id.radio_char:
                if (checked) {
                    specialChar = true;
                    isTouched = true;
                } else {
                    specialChar = false;
                    if (!upperCase && !lowerCase && !useNumber) {
                        isTouched = false;
                    }
                }
                break;

            case R.id.radio_upper:
                if (checked) {
                    upperCase = true;
                    isTouched = true;
                } else {
                    upperCase = false;
                    if (!specialChar && !lowerCase && !useNumber) {
                        isTouched = false;
                    }

                }
                break;

            case R.id.radio_lower:
                if (checked) {
                    lowerCase = true;
                    isTouched = true;
                } else {
                    lowerCase = false;
                    if (!upperCase && !specialChar && !useNumber) {
                        isTouched = false;
                    }
                }
                break;

            case R.id.radio_number:
                if (checked) {
                    useNumber = true;
                    isTouched = true;
                } else {
                    useNumber = false;
                    if (!upperCase && !specialChar && !lowerCase) {
                        isTouched = false;
                    }
                }
                break;

        }
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
                    try {
                        reqLength = Integer.parseInt(length.getText().toString());
                        isTouched = true;
                    } catch (Exception e) {
                        reqLength = -1;
                    }

                    if (isTouched) {
                        if (reqLength < 3 && reqLength != -1) {
                            reqLength = 3;
                            length.setText("" + 3);
                            Toast.makeText(MainActivity.this, "Minimum is 3", Toast.LENGTH_SHORT).show();
                        } else if (reqLength > 35) {
                            reqLength = 35;
                            length.setText("" + 35);
                            Toast.makeText(MainActivity.this, "Maximum is 35", Toast.LENGTH_SHORT).show();
                        }
                        chryptoAlgorithm = new StartAlgorithm(keyString, passString, reqLength, lowerCase, upperCase, specialChar,useNumber);
                    } else {
                        chryptoAlgorithm = new StartAlgorithm(keyString, passString);
                    }

                    finalPass.setText(chryptoAlgorithm.getPass());

                    Toast.makeText(MainActivity.this, "Password is copied to clipboard", Toast.LENGTH_SHORT).show();

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", chryptoAlgorithm.getPass());
                    clipboard.setPrimaryClip(clip);

                    finalPass.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(MainActivity.this, "Password is copied to clipboard", Toast.LENGTH_SHORT).show();
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("label", chryptoAlgorithm.getPass());
                            clipboard.setPrimaryClip(clip);

                            return true;
                        }
                    });

                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Fill in both fields please", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    openDialogForInput();
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
        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey = "Instagram";
                tv.setText(imgKey);
            }
        });
        ImageView bad = (ImageView) v.findViewById(R.id.badoo);
        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey ="Google +";
                tv.setText(imgKey);
            }
        });
        ImageView woff = (ImageView) v.findViewById(R.id.woff);
        woff.setVisibility(View.INVISIBLE);
        woff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey = "Woff";
                tv.setText(imgKey);
            }
        });
        ImageView tind = (ImageView) v.findViewById(R.id.tinder);
        tind.setVisibility(View.INVISIBLE);
        tind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey = "Tinder";
                tv.setText(imgKey);
            }
        });
        ImageView twitt = (ImageView) v.findViewById(R.id.twitter);
        twitt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imgKey = "Twitter";
                tv.setText(imgKey);
            }
        });
        pass.setHint("Write your password");
        alert.setView(v);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String passString = pass.getText().toString();
                try {
                    try {
                        reqLength = Integer.parseInt(length.getText().toString());
                        isTouched = true;
                    } catch (Exception e) {
                        reqLength = -1;
                    }

                    if (isTouched) {
                        if (reqLength < 3 && reqLength != -1) {
                            reqLength = 3;
                            length.setText("" + 3);
                            Toast.makeText(MainActivity.this, "Minimum is 3", Toast.LENGTH_SHORT).show();
                        } else if (reqLength > 35) {
                            reqLength = 35;
                            length.setText("" + 35);
                            Toast.makeText(MainActivity.this, "Maximum is 35", Toast.LENGTH_SHORT).show();
                        }

                        chryptoAlgorithm = new StartAlgorithm(imgKey, passString, reqLength, lowerCase, upperCase, specialChar,useNumber);

                    } else {
                        chryptoAlgorithm = new StartAlgorithm(imgKey, passString);
                    }

                    Toast.makeText(MainActivity.this, "Password is copied to clipboard", Toast.LENGTH_SHORT).show();

                    finalPass.setText(chryptoAlgorithm.getPass());

                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", chryptoAlgorithm.getPass());
                    clipboard.setPrimaryClip(clip);

                    finalPass.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            Toast.makeText(MainActivity.this, "Password is copied to clipboard", Toast.LENGTH_SHORT).show();
                            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                            ClipData clip = ClipData.newPlainText("label", chryptoAlgorithm.getPass());
                            clipboard.setPrimaryClip(clip);

                            return true;
                        }
                    });
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error occured.." + e, Toast.LENGTH_SHORT).show();
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
