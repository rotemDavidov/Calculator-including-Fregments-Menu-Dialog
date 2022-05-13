package com.example.ex2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;

public class ExitDialogClass extends DialogFragment {

    public static ExitDialogClass newInstance() {
        return  new ExitDialogClass();  //we don't create bundle because we don't pass arguments
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setTitle("close calculator app")
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        }
                )
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                // close the dialog box
                                dialog.dismiss();
                            }
                        }
                )
                .create();
    }
}


