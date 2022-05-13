package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONObject;

public class viewFriends extends AppCompatActivity
{
    private int userID;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewfriends);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        userID = getIntent().getIntExtra(StatusView.USER_ID, -1);
        showFriends();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu)
    {
        getMenuInflater().inflate(R.menu.friend_view_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == R.id.viewPending)
        {
            Intent pendingView = new Intent(this, pendingRequests.class);
            pendingView.putExtra("USERID", userID);
            startActivity(pendingView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFriends()
    {
        ContentValues params = new ContentValues();
        params.put("uId", userID);

        Requests.request(this, "showFriends", params, response ->
        {
            JSONArray friends = new JSONArray(response);

            if (friends.getJSONObject(0).getString("Username").equals("null"))
            {
                Requests.showMessage(this, "No friends yet");
                return;
            }

            for (int i = 0; i < friends.length(); i++)
            {
                JSONObject friend = friends.getJSONObject(i);

                String username = friend.getString("Username");

                runOnUiThread(() -> getSupportFragmentManager().beginTransaction()
                        .add(R.id.friendList, UserList.newInstance(username, userID, -1)).commit());
            }
        });
    }
}