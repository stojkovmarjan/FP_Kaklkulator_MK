package com.mk.busystuff.zxm.fp_kaklkulator_mk;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

/**
 * Created by marjan on 13.7.16.
 */
public class CustomWatcher implements TextWatcher {

    private boolean mWasEdited = false;

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        /*
          if (mWasEdited){
            mWasEdited = false;
            return;
        }
         */



        // get entered value (if required)
        String enteredValue  = s.toString();

        String newValue = "new value";
        Log.d("AAAAAAAAAA",enteredValue);
        // don't get trap into infinite loop
        mWasEdited = true;
        // just replace entered value with whatever you want
        //s.replace(0, s.length(), newValue);
    }
}
