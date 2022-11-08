package com.technlogi.tt;

import android.app.Dialog;
import android.content.Context;

public class LoadingDialog {

    private Dialog dialog;

    public LoadingDialog (Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_loading);
        dialog.getWindow().setBackgroundDrawableResource(R.color.fully_transparent);
    }

    public void showLoadingDialog() {
        dialog.show();
    }

    public void dismissLoadingDialog() {
        dialog.dismiss();
    }
}
