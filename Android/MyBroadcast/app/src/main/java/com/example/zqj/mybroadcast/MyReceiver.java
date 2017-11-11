package com.example.zqj.mybroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {
    public MyReceiver() {
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //method
        String msg=intent.getStringExtra("msg");
        System.out.println(intent.getAction());
        System.out.println(msg);
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
