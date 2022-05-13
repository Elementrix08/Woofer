package com.Woofer;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity
{
    Button btnLogin, btnRegister;
    EditText edtUsername, edtPassword;

    public static String USER = "User";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this::onLogin_click);

        btnRegister = findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this::onRegister_click);
    }

    boolean validateUI(String username, String password)
    {
        if (username.isEmpty())
        {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (password.isEmpty())
        {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    boolean validateLogin(JSONObject user, String password) // Checking if passwords match
    {
        password = Requests.caesarCipher(password);

        try
        {
            if (!password.equals(user.getString("HashedPassword"))) // Add ceaser cypher encoding
            {
                Requests.showMessage(this, "Incorrect password");
                return false;
            }
        } catch (JSONException ignored) {}

        return true;
    }

    public void onLogin_click(View v)
    {
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if (!validateUI(username, password))
            return;

        ContentValues params = new ContentValues();
        params.put("uName", username);
        params.put("parameter", "");

        Requests.request(this, "checkUser", params, response -> // Accessing checkUser.php file
        {
            try
            {
                JSONObject userData = new JSONArray(response).getJSONObject(0);

                if (!validateLogin(userData, password))
                    return;

                Intent frmStatus = new Intent(MainActivity.this, StatusView.class);
                frmStatus.putExtra(USER, new User(userData)); // Parse new user
                clearActivity();
                startActivity(frmStatus); // Show login form
            } catch (JSONException e)
            {
                Requests.showMessage(this, "User does not exist");
            }
        });
    }

    public void onRegister_click(View v)
    {
        clearActivity();
        Intent frmRegister = new Intent(MainActivity.this, RegisterView.class);
        startActivity(frmRegister);
    }

    private void clearActivity()
    {
        if (edtUsername != null)
            edtUsername.setText("");

        if (edtPassword != null)
            edtPassword.setText("");
    }
}