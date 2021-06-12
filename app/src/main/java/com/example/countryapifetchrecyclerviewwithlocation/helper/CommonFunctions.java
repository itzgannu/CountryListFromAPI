package com.example.countryapifetchrecyclerviewwithlocation.helper;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class CommonFunctions {
    public void LogCatD(String TAG, String message){
        Log.d(TAG, message);
    }
    public void LogCatE(String TAG, String message){
        Log.e(TAG, message);
    }
    public void ToastMessageLong(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.show();
    }
    public void ToastMessageShort(Context context, String message){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
