package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.atomic.AtomicBoolean;

public class RegisterView extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.btnRegister);
        register.setOnClickListener(this::processRegistration);
    }

    private void processRegistration(View v)
    {
        EditText edtUsername = findViewById(R.id.username);
        EditText edtEmail = findViewById(R.id.email);
        EditText edtPassword = findViewById(R.id.password);
        EditText edtConfirmPassword = findViewById(R.id.confirm_password);

        String username = edtUsername.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        String passConfirm = edtConfirmPassword.getText().toString();

        if (!validateUI(username, email, password, passConfirm))
            return;

        final String encodedPassword = Requests.caesarCipher(password);
        ContentValues params = new ContentValues();
        params.put("uName", username);
        params.put("parameter", "");

        Requests.request(this, "checkUser", params, response ->
        {
            try
            {
                new JSONArray(response).getJSONObject(0); // Try to get the user JSON Obj
                Requests.showMessage(this, "Username already exists");
            } catch (JSONException e) // Means the user doesn't exist so we can use this username
            {
                ContentValues userDetails = new ContentValues();
                userDetails.put("email", email);
                userDetails.put("uName", username);
                userDetails.put("pwd", encodedPassword);

                Requests.request(this, "newUser", userDetails, responseData -> {});
                Requests.showMessage(this, "Account created successfully");
                finish();
            }
        });
    }

    private boolean validateUI(String username, String email, String password, String confirmPassword)
    {
        String blankOption = "";

        if (username.isEmpty())
            blankOption = "Username";

        else if (email.isEmpty())
            blankOption = "Email";

        else if (password.isEmpty())
            blankOption = "Password";

        else if (confirmPassword.isEmpty())
            blankOption = "Confirm Password";

        if (!blankOption.isEmpty())
        {
            Requests.showMessage(this, blankOption + " cannot be empty");
            return false;
        }

        if (!password.equals(confirmPassword))
        {
            Requests.showMessage(this, "Passwords do not match");
            return false;
        }

        if (!email.contains("@"))
        {
            Requests.showMessage(this, "Invalid email");
            return false;
        }

        if (username.length() < 4)
        {
            Requests.showMessage(this, "Username must be 4 or more characters");
            return false;
        }

        if (password.length() < 4)
        {
            Requests.showMessage(this, "Password must be 4 or more characters");
            return false;
        }

        return true;
    }
}