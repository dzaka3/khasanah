package com.khasanah.features.utils.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import com.wang.avi.AVLoadingIndicatorView;
import com.khasanah.R;

public class CustomAVProgressDialog extends ProgressDialog {
    private AVLoadingIndicatorView avi;

    public static ProgressDialog showProgressDialog(Context context) {
        CustomAVProgressDialog customProgressDialog = new CustomAVProgressDialog(context);
        customProgressDialog.setCancelable(false);
        customProgressDialog.setIndeterminate(true);

        return customProgressDialog;
    }

    private CustomAVProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window w = getWindow();
        if(w != null){
            w.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            setContentView(R.layout.custom_av_progress_dialog);

            avi= findViewById(R.id.avi);
            avi.setIndicator("BallPulseIndicator");
        }
    }

    @Override
    public void show() {
        try {
            super.show();
            avi.smoothToShow();
        }catch (Exception ignored){}
    }

    @Override
    public void dismiss() {
        try {
            super.dismiss();
            avi.smoothToHide();
        } catch (Exception ignored){}
    }
}
