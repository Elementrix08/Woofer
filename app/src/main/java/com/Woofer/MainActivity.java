package com.Woofer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::onLogin_click);

        Button btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this::onRegister_click);
    }

    public void onLogin_click(View v)
    {
        EditText edtUsername = findViewById(R.id.edtUsername);
        EditText edtPassword = findViewById(R.id.edtPassword);

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        /* TODO
        Do a get request to check if user exists
        and then only direct them to the next page.
        If the user doesn't exist, tell them and prompt to register
        */
    }

    public void onRegister_click(View v)
    {
        /* TODO
           Direct user to the registration page
           with the username they filled if they did
        */
    }
}