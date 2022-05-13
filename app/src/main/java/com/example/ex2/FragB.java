package com.example.ex2;
//import android.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.Locale;
public class FragB extends Fragment  {

    static float val;
    public static View Fragb;
    FragBListener listener; // hold the mainactivity referance


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // -------------------------FRAGMENTS METHODS ---------------------------------------------

    @Override
    public void onAttach(@NonNull Context context) {
        //this connect our main activity with the B fragment when the context var is the main activity
        try{
            this.listener = (FragBListener)context;
        }catch(ClassCastException e){
            throw new ClassCastException("the class " +
                    getActivity().getClass().getName() +
                    " must implements the interface 'FragBListener'");
        }
        super.onAttach(context);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Fragb  = inflater.inflate(R.layout.frag_b, container,false);
        return Fragb;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    //---------------------------------------------------------------------------------------------------------------------


    //----------------------------------- methods that being called from mainActivity -------------------------------------
    //the activity informs fragB about new click in fragA
    public void notifyFragB(float sum){
        val = sum;
        ((TextView) Fragb.findViewById(R.id.textView5)).setText(String.format("%." + SeekbarDialogClass.move + "f", val));
    }

    public static void precsionVal(int move){ //you may ask yourself why i make another method like notifyFragB snd me a msg
        ((TextView) Fragb.findViewById(R.id.textView5)).setText(String.format("%." + move + "f", val));
    }


    //the interface of this fragment that include the methods
    public interface FragBListener{
    }


    public void disableButtons(){
        try{
        ((TextView)Fragb.findViewById(R.id.textView5)).setText("");
        SeekbarDialogClass.move=0;
        }
        catch(Exception e) {
            return;
        }
    }

    public String getCurrentString() { //getting the string in result field
        TextView tx = (TextView) getView().findViewById(R.id.textView5);
        return tx.getText().toString();
    }

    //----------------------------------------- MENU METHODS --------------------------------------------------

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) { //adding the settings logo
        inflater.inflate(R.menu.settings, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //action while press
        return true;
    }
}

    //-----------------------------------------------------------------------------------------------------------