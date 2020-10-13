package com.inopetech.safeparcel.util;

import android.app.ProgressDialog;
import android.content.ContentProvider;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;


public class NetworkCalls {
    private static ContentProvider alpha_cp;
    private static NetworkCalls net = null;
    private ProgressDialog progressDialog;


    private NetworkCalls() {
    }

    public static NetworkCalls getInstance() {
        if (net == null) {
            net = new NetworkCalls();
        }

        return net;
    }

    public void initCall(Context context, String url, Map<String, String> params, NetworkInterface listener) {

    }

    private void hideDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        Log.d("DIALOG_PRO", "kill dialog");

    }

    private void showProgress(Context context) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Please Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }
}
