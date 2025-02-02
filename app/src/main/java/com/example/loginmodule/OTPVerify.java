package com.example.loginmodule;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OTPVerify extends AppCompatActivity {

    private EditText otp1, otp2, otp3, otp4, otp5, otp6;
    private Button btnVerifyOtp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverify);

        // Initialize EditTexts
        otp1 = findViewById(R.id.otp1);
        otp2 = findViewById(R.id.otp2);
        otp3 = findViewById(R.id.otp3);
        otp4 = findViewById(R.id.otp4);
        otp5 = findViewById(R.id.otp5);
        otp6 = findViewById(R.id.otp6);

        // Initialize Button
        btnVerifyOtp = findViewById(R.id.btn_verify_otp);

        // Set OnClickListener for the button
        btnVerifyOtp.setOnClickListener(v -> {
            if (isOtpEntered()) {
                Toast.makeText(OTPVerify.this, "OTP Verified", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(OTPVerify.this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
            }
        });

        // Add TextWatchers to move focus automatically
        setupOtpEditTexts();
    }

    private boolean isOtpEntered() {
        return !otp1.getText().toString().isEmpty() &&
                !otp2.getText().toString().isEmpty() &&
                !otp3.getText().toString().isEmpty() &&
                !otp4.getText().toString().isEmpty() &&
                !otp5.getText().toString().isEmpty() &&
                !otp6.getText().toString().isEmpty();
    }

    private void setupOtpEditTexts() {
        otp1.addTextChangedListener(new OtpTextWatcher(otp1, otp2));
        otp2.addTextChangedListener(new OtpTextWatcher(otp2, otp3));
        otp3.addTextChangedListener(new OtpTextWatcher(otp3, otp4));
        otp4.addTextChangedListener(new OtpTextWatcher(otp4, otp5));
        otp5.addTextChangedListener(new OtpTextWatcher(otp5, otp6));
        otp6.addTextChangedListener(new OtpTextWatcher(otp6, null));

        // Set onKeyListener to move to previous field on backspace
        setBackspaceListener(otp1, null);
        setBackspaceListener(otp2, otp1);
        setBackspaceListener(otp3, otp2);
        setBackspaceListener(otp4, otp3);
        setBackspaceListener(otp5, otp4);
        setBackspaceListener(otp6, otp5);
    }

    private void setBackspaceListener(final EditText current, final EditText previous) {
        current.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                if (current.getText().toString().isEmpty() && previous != null) {
                    previous.requestFocus();  
                    return true;
                }
            }
            return false;
        });
    }

    private class OtpTextWatcher implements TextWatcher {
        private EditText currentEditText;
        private EditText nextEditText;

        public OtpTextWatcher(EditText currentEditText, EditText nextEditText) {
            this.currentEditText = currentEditText;
            this.nextEditText = nextEditText;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 1 && nextEditText != null) {
                nextEditText.requestFocus();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    }
}