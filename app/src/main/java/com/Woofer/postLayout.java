package com.Woofer;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link postLayout#newInstance} factory method to
 * create an instance of this fragment.
 */
public class postLayout extends Fragment
{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String POSTCONTENT_PARAM = "postContent";
    private static final String USERNAME_PARAM = "Username";
    private static final String DATE_PARAM = "date";

    // TODO: Rename and change types of parameters
    private static String postContent;
    private static String username;
    private static String date;

    public static postLayout newInstance(String username, String postContent, String date)
    {
        postLayout fragment = new postLayout();
        Bundle args = new Bundle();
        args.putString(POSTCONTENT_PARAM, postContent);
        args.putString(USERNAME_PARAM, username);
        args.putString(DATE_PARAM, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (getArguments() != null)
        {
            username = getArguments().getString(USERNAME_PARAM);
            postContent = getArguments().getString(POSTCONTENT_PARAM);
            date = getArguments().getString(DATE_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_post_layout, container, false);

        TextView uname = view.findViewById(R.id.username);
        TextView statusContent = view.findViewById(R.id.postContent);
        TextView datePosted = view.findViewById(R.id.datePosted);

        uname.setText(username);
        statusContent.setText(postContent);
        datePosted.setText(date);

        return view;
    }
}