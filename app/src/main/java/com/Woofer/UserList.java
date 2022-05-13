package com.Woofer;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;

import java.util.ArrayList;

public class UserList extends Fragment
{
    private static final String USER_ID = "uId";
    private static final String FRIEND_ID = "fId";
    private static final String USERNAME = "USERNAME";

    private String username;
    private int ID, fID;

    public static UserList newInstance(String username, int ID, int fID)
    {
        UserList fragment = new UserList();
        Bundle args = new Bundle();

        args.putString(USERNAME, username);
        args.putInt(USER_ID, ID);
        args.putInt(FRIEND_ID, fID);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            ID = getArguments().getInt(USER_ID);
            fID = getArguments().getInt(FRIEND_ID);
            username = getArguments().getString(USERNAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);
        TextView uName = view.findViewById(R.id.username);
        Button btnAdd = view.findViewById(R.id.addUser);
        TextView mutFriends = view.findViewById(R.id.lblMutFriends);

        uName.setText(username); // Set the username

        if (fID == -1) // Represents just a user list instead of friends list
        {
            btnAdd.setVisibility(View.INVISIBLE);
            return view;
        }

        ContentValues params = new ContentValues();
        params.put(USER_ID, ID);
        params.put(FRIEND_ID, fID);

        Activity currAct = requireActivity();

        btnAdd.setOnClickListener((View v) ->
        {
            Requests.request(currAct, "newPendingRequest", params, response -> {});
            Requests.showMessage(currAct, "Friend request has been sent");
            btnAdd.setEnabled(false); // Disable once user is selected
        });

        Requests.request(currAct, "showMutualFriends2Users", params, response ->
        {
            JSONArray mutualFriends = new JSONArray(response);

            int n = mutualFriends.length();

            if (n == 0)
                return;

            String message;

            if (n == 1)
                message = n + " mutual friend";
            else
                message = n + " mutual friends";

            currAct.runOnUiThread(() ->
            {
                mutFriends.setText(message);
                mutFriends.setOnClickListener(mainView ->
                {
                    Intent frmMutualFriends = new Intent(currAct, mutualFriends.class);
                    frmMutualFriends.putExtra("userID", ID);
                    frmMutualFriends.putExtra("friendID", fID);
                    startActivity(frmMutualFriends);
                });

                mutFriends.setVisibility(View.VISIBLE);
            });
        });

        return view;
    }
}