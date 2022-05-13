package com.Woofer;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class loadingDialog
{
    private final Activity activity;
    private AlertDialog dialog;

    loadingDialog(Activity activity)
    {
        this.activity = activity;
    }

    public void startLoading()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater infalter = activity.getLayoutInflater();
        builder.setView(infalter.inflate(R.layout.customdialog, null));
        builder.setCancelable(false);

        activity.runOnUiThread(() ->
        {
            dialog = builder.create();
            dialog.show();
        });
    }

    public void closeDialog()
    {
        dialog.dismiss();
    }
}
