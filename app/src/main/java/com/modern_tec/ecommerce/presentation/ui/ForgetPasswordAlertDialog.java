package com.modern_tec.ecommerce.presentation.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

public class ForgetPasswordAlertDialog {

    private final Context context;

    public ForgetPasswordAlertDialog(Context context) {
        this.context = context;
    }

    public void createAlertDialog(String title, OnClickOkListener okButton) {
        EditText emailEditText = new EditText(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(15, 5, 15, 5);
        emailEditText.setLayoutParams(params);
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage("Please enter your email to sent reset link to it.")
                .setView(emailEditText)
                .setPositiveButton("Send", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String email = emailEditText.getText().toString().trim();
                        if (!email.isEmpty()) {
                            okButton.onClick(email);
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create().show();
    }

    public interface OnClickOkListener {
        void onClick(String verifiedEmail);
    }
}
