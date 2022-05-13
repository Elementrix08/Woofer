package com.Woofer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class createPost extends AppCompatActivity
{
    Button btnPost;
    private int userID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        userID = getIntent().getIntExtra(StatusView.USER_ID, -1);

        btnPost = findViewById(R.id.postStatus);
        btnPost.setOnClickListener(this::postStatus);
    }

    private Boolean validateUI()
    {
        TextView message = findViewById(R.id.edtMessage);

        if (message.getText().toString().isEmpty())
        {
            Requests.showMessage(this, "Status message cannot be empty");
            return false;
        }

        return true;
    }

    private void postStatus(View v)
    {
        if (!validateUI())
            return;

        EditText edtMessage = findViewById(R.id.edtMessage);
        String message = edtMessage.getText().toString();

        ContentValues params = new ContentValues();
        params.put("uId", userID);
        params.put("content", message);

        Requests.request(this, "newPost", params, response -> {});
        Requests.showMessage(this, "Status posted");
        finish();
    }
}