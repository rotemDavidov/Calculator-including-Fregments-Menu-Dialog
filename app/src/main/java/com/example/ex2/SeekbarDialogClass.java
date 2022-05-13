package com.example.ex2;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class SeekbarDialogClass extends DialogFragment implements SeekBar.OnSeekBarChangeListener{
    static int move=0; //can do it because we can do inner class
    boolean startSeekBar=false;
    double ex = 123.0;
    static SeekBar SeekBar_;
    static View seekBarView;

    public static SeekbarDialogClass newInstance() {
        return new SeekbarDialogClass();
    }



    //----------------------------SEEKBAR METHODS------------------------------------------------
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        move = i;
        if (i == 0) {
            ((TextView) seekBarView.findViewById(R.id.textView6)).setText("Example: 123");

        } else {
            //FragB.Fragb - the same way to implement an inner class, we make the view public
            //and make sure we dont call it from main activity to still implement an interface
            ((TextView) seekBarView.findViewById(R.id.textView6)).setText(String.format("Example: " + "%." + i + "f", ex));
        }
        FragB.precsionVal(move);
    }
    @Override
    public void onStartTrackingTouch (SeekBar seekBar){

    }

    @Override
    public void onStopTrackingTouch (SeekBar seekBar){

    }

    //------------------------------------------------------------------------------------------------------------------------

    //---------------------------------------------- DIALOG METHODS ---------------------------------------------------------

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity()); //creating the dialog window
        //getting the view of the sublayout because we cant reach it from the frag b
        seekBarView = getActivity().getLayoutInflater().inflate(R.layout.sublayout, null); //getting the SeekBar view

        SeekBar_ = (SeekBar) seekBarView.findViewById(R.id.seekbar);
        SeekBar_.setOnSeekBarChangeListener(this);
        //if there is no solution we don't let the user touch the seekbar
        if(((TextView)FragB.Fragb.findViewById(R.id.textView5)).getText().toString().equals(""))
            SeekBar_.setEnabled(false);

        else
            SeekBar_.setEnabled(true);

        //getting the args that we send in the main
        Bundle args = getArguments();
        int prog = args.getInt("move");
        SeekBar_.setProgress(prog);

        // Inflate and set the layout for the dialog

        builder.setTitle("Set the number precision")
                .setView(seekBarView)
                .setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SeekBar_.setProgress(move); //newest move
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dismiss();
                            }
                        }
                );
        return builder.create();
    }


}
