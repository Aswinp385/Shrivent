package com.develop.eventmanagement;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;

public class LoadingView {
    Dialog dialog;
    //ProgressDialog dialog;

    public LoadingView(Context context) {
       /* dialog=new ProgressDialog(context);
        dialog.setCancelable(false);
        dialog.setMessage("Processing...");*/

        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //dialog.setTitle("Processing...");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loading_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

    }

    public void showLoadingView() {
        dialog.show();
    }

    public void hideLoadingView() {
        dialog.dismiss();
    }
}
