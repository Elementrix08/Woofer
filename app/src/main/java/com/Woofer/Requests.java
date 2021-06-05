package com.Woofer;

import android.app.Activity;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Requests
{
    final private static String ERROR_MSG = "Unable to connect to server";

    public static void request(Activity actForm, String url)
    {
        OkHttpClient client = new OkHttpClient();
        Request req = new Request.Builder().url(url).build();

        client.newCall(req).enqueue(new Callback()
        {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e)
            {
                Toast.makeText(actForm, ERROR_MSG, Toast.LENGTH_SHORT).show(); // Show error message
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response)
            {
                String responseData = response.body().toString();
            }
        });
    }
}
