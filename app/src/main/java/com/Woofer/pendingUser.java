package com.Woofer;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class pendingUser extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_ID = "uId";
    private static final String FRIEND_ID = "fId";
    private static final String USERNAME = "USERNAME";

    private String username;
    private int ID, fID;

    public static pendingUser newInstance(String username, int ID, int fID)
    {
        pendingUser fragment = new pendingUser();
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

    Button btnAccept, btnReject;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_pending_user, container, false);
        TextView uName = view.findViewById(R.id.username);

        btnAccept = view.findViewById(R.id.accept);
        btnReject = view.findViewById(R.id.decline);

        uName.setText(username); // Set the username

        ContentValues params = new ContentValues();
        params.put(USER_ID, ID);
        params.put(FRIEND_ID, fID);

        btnAccept.setOnClickListener(v ->
        {
            removePending();
            Requests.request(requireActivity(), "newFriends", params, response -> {});
            params.clear();
            params.put(USER_ID, fID);
            params.put(FRIEND_ID, ID);
            Requests.request(requireActivity(), "newFriends", params, response -> {});
        });

        btnReject.setOnClickListener(v -> removePending());
        return view;
    }

    private void removePending()
    {
        ContentValues params = new ContentValues();
        params.put(USER_ID, fID);
        params.put(FRIEND_ID, ID);

        Requests.request(requireActivity(), "deletePendingRequest", params, response ->
            requireActivity().runOnUiThread(() ->
            {
                btnAccept.setVisibility(View.INVISIBLE);
                btnReject.setVisibility(View.INVISIBLE);
            }));
    }
}