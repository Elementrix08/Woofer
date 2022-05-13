package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class mutualFriends extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutual_friends);

        int userID = getIntent().getIntExtra("userID", -1);
        int friendID = getIntent().getIntExtra("friendID", -1);

        Button btnBack = findViewById(R.id.back);
        btnBack.setOnClickListener((View v) -> finish());

        ContentValues params = new ContentValues();
        params.put("uId", userID);
        params.put("fId", friendID);

        Requests.request(this, "showMutualFriends2Users", params, response ->
        {
            JSONArray mutualFriends = new JSONArray(response);

            int n = mutualFriends.length();

            if (n == 0)
                return;

            for (int i = 0; i < n; i++)
            {
                String username = mutualFriends.getJSONObject(i).getString("Username");
                runOnUiThread(() -> getSupportFragmentManager().beginTransaction()
                        .add(R.id.mutFriendList, UserList.newInstance(username, 0, -1)).commit());
            }
        });
    }
}