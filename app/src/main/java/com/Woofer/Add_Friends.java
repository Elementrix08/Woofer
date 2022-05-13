package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.ContentValues;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Add_Friends extends AppCompatActivity
{
    Button btnSearch;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);

        userID = getIntent().getIntExtra(StatusView.USER_ID, -1);

        btnSearch = findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(this::onSearch);
        showSuggested();
    }

    private void showSuggested()
    {
        ContentValues params = new ContentValues();
        params.put("uId", userID);

        Requests.request(this, "showSuggestedFriends", params, response ->
        {
            JSONArray friends = new JSONArray(response);
            display(friends);
        });
    }

    private void onSearch(View v)
    {
        EditText edtUsername = findViewById(R.id.edtUsername);
        String username = edtUsername.getText().toString();

        if (username.length() < 4)
            return;

        ContentValues params = new ContentValues();
        params.put("uName", username);
        params.put("parameter", "search");

        Requests.request(this, "checkUser", params, response ->
        {
            if (!response.isEmpty())
            {
                JSONArray friends = new JSONArray(response);
                display(friends);
            }
            else
                runOnUiThread(() -> getSupportFragmentManager().beginTransaction()
                        .add(R.id.usersList, UserList.newInstance("No users found", userID, -1)).commit());
        });
    }

    private void display(JSONArray friends) throws JSONException
    {
        runOnUiThread(() ->
        {
            LinearLayout userList = findViewById(R.id.usersList);
            userList.removeAllViews();
        });

        for (int i = 0; i < friends.length(); i++)
        {
            JSONObject user = friends.getJSONObject(i);
            String currUsername = user.getString("Username");

            int friendID = user.getInt("UserID");

            if (user.has("FriendID")) // Used for suggested friends
            {
                friendID = user.getInt("FriendID");

                if (userID == friendID)
                    continue;
            }

            int finalFriendID = friendID;

            runOnUiThread(() -> getSupportFragmentManager().beginTransaction().
                            add(R.id.usersList, UserList.newInstance(currUsername, userID, finalFriendID)).commit());
        }
    }
}