package com.example.ex2;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements FragA.FragAListener,FragB.FragBListener {//implements SeekBar.OnSeekBarChangeListener {

    private FragB fragB=null;
    float val=0;
    int move;
    Bundle bundle;
    public static FragmentManager obj;

    @Override
    //MEMBER CLASS
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        obj = getSupportFragmentManager();

        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

        if ((getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)){
            if (fragB != null) {
                getSupportFragmentManager().beginTransaction()
                        .show(fragB)
                        .addToBackStack(null)
                        .commit();
            }
            else {
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.fragContainer, FragB.class,null, "FRAGB")
                        .commit();
            }
            getSupportFragmentManager().executePendingTransactions();
        }

    }

    //--------------------------------------------MENU METHODS -----------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu){ //present the menu in the view
        getMenuInflater().inflate(R.menu.exit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){ //action while press
        switch (item.getItemId()){
            case R.id.menuExit:
                ExitDialogClass newFragment = ExitDialogClass.newInstance(); //create dialog box with listeners
                newFragment.show(getSupportFragmentManager(), "exitDialog"); //show the dialog for action
                break;
            case R.id.menuSetting:
                SeekbarDialogClass settingsDialog = SeekbarDialogClass.newInstance();
                bundle = new Bundle();
                bundle.putInt("move", SeekbarDialogClass.move);
                settingsDialog.setArguments(bundle);
                settingsDialog.show(getSupportFragmentManager(), "settingsDialog");
                break;
        }

        return  true;
    }

    //---------------------------------------------------------------------------------------------------------------------

    //-------------------------------------------- CHANGING LAND/PORT SCAPE ------------------------------------------------
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        try { //if frag b exist we already present result
            outState.putString("str", fragB.getCurrentString());
            outState.putFloat("res", val);
            outState.putInt("move", SeekbarDialogClass.move);
        }
        catch (Exception e) {
            outState.putString("str", ""); //if there is no frag b
        }

    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        FragB fragB;
        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");

        super.onRestoreInstanceState(savedInstanceState);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .show(fragB)
                    .addToBackStack("BBB")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        String cs = savedInstanceState.getString("str");
        if (!cs.equals("")) { //there is a val to update
            val = savedInstanceState.getFloat("res");
            move = savedInstanceState.getInt("move");
            fragB.notifyFragB(val); //val needs to be update because we change to land/port sacpe
            fragB.precsionVal(move); //after we update the val we update the precision of the val
            SeekbarDialogClass.move=move; //we will update the the movement because on new bundle the move need to be taken from the static move field
            }
    }

    //-------------------------------------------------------------------------------------------------------------------

    //---------------------------------- FRAGB INTERFACE METHODS THAT NEED TO BE IMPLEMENT ------------------------------

    public void sendingValToCalculate(View view,float num1,float num2){
        int id = ((Button)view).getId();
        String btnName = getResources().getResourceEntryName(id);
        if(btnName.equals("sum")) val = num1+num2;
        else if(btnName.equals("sub")) val = num1-num2;
        else if(btnName.equals("mul")) val = num1*num2;
        else if(btnName.equals("divide")) val = num1/num2;

        addFragB();
        //sending frag b the val to present
        fragB.notifyFragB(val);

    }

    //------------------------------------------------------------------------------------------------------------------

    // ------------------------------------------- rest of the methods -------------------------------------------------
    private void addFragB(){
        //before we notify frag b we want to upload him if we are in portrait view
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragContainer, FragB.class, null,"FRAGB")
                    .addToBackStack("BBB")
                    .commit();
            getSupportFragmentManager().executePendingTransactions();
        }
        fragB = (FragB) getSupportFragmentManager().findFragmentByTag("FRAGB");
    }

    public void disable(){
        if(fragB!=null)
            fragB.disableButtons();
    }




}