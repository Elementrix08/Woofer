package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StatusView extends AppCompatActivity
{
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.statusview_menu, menu);

        return true;
    }

    public User user;
    public static final String USER_ID = "USERID";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statusview);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        user = getIntent().getParcelableExtra(MainActivity.USER);
        showFriends();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.addFriend:
                Intent addFriend = new Intent(StatusView.this, Add_Friends.class);
                addFriend.putExtra(USER_ID, user.getUserID());
                startActivity(addFriend);
                showFriends();
                return true;

            case R.id.viewFriends:
                Intent viewFriends = new Intent(this, viewFriends.class);
                viewFriends.putExtra(USER_ID, user.getUserID());
                startActivity(viewFriends);
                return true;

            case R.id.createPost:
                Intent createPost = new Intent(this, createPost.class);
                createPost.putExtra(USER_ID, user.getUserID());
                startActivity(createPost);
                return true;
            case R.id.refresh:
                showFriends();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showFriends()
    {
        ContentValues params = new ContentValues();
        params.put("offset", 0);
        params.put("uId", user.getUserID().toString());

        Requests.request(this, "getPosts", params, response ->
        {
            JSONArray friends = new JSONArray(response);

            runOnUiThread(() ->
            {
                LinearLayout main = findViewById(R.id.friendList);
                main.removeAllViews();

                String username, content, date;

                for (int i = 0; i < friends.length(); i++)
                {
                    try
                    {
                        JSONObject friend = friends.getJSONObject(i);
                        username = friend.getString("Username");
                        content = friend.getString("PostContent");
                        date = friend.getString("DatePosted");

                        getSupportFragmentManager().beginTransaction()
                                .add(R.id.friendList, postLayout.newInstance(username, content, date)).commit();
                    } catch (JSONException e) { break; }
                }
            });
        });
    }
}