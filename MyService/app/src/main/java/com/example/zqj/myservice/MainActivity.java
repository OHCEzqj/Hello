package com.example.zqj.myservice;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Intent intent=new Intent(getBaseContext(),MyService.class);
        Button btstart=(Button)findViewById(R.id.button1);
        Button btstop=(Button)findViewById(R.id.button2);
        Button btbind=(Button)findViewById(R.id.button3);
        Button btunbind=(Button)findViewById(R.id.button4);
        btstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //启动服务
                startService(intent);
            }
        });
        btstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //停止服务
                stopService(intent);
            }
        });
        btbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bindService(intent,connection, Context.BIND_AUTO_CREATE);
            }
        });
        btunbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               unbindService(connection);
            }
        });

    }//onCreate
    ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
//            method
            System.out.println("onServiceConnected");
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
//            method
            System.out.println("onServiceDisconnected");
        }
    };
}
