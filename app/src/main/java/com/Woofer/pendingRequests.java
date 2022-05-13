package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class pendingRequests extends AppCompatActivity
{
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

        Button back = findViewById(R.id.back);
        back.setOnClickListener(view -> finish());

        userID = getIntent().getIntExtra("USERID", -1);

        showPending();
    }

    private void showPending()
    {
        ContentValues params = new ContentValues();
        params.put("fId", userID);

        Requests.request(this, "showPendingRequests", params, response ->
        {
            JSONArray friends = new JSONArray(response);

            if (friends.length() == 0)
            {
                Requests.showMessage(this, "No friends pending");
                return;
            }

            for (int i = 0; i < friends.length(); i++)
            {
                JSONObject friend = friends.getJSONObject(i);
                int friendID = friend.getInt("UserID");

                String username = friend.getString("Username");
                runOnUiThread(() -> getSupportFragmentManager().beginTransaction()
                        .add(R.id.pendingList, pendingUser.newInstance(username, userID, friendID)).commit());
            }
        });
    }
}