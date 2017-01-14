package com.example.zqj.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
    // System.out 用于测试
    public void onCreate(){
        super.onCreate();
        System.out.println("Service Created");
        Toast.makeText(this,"Service Created",Toast.LENGTH_LONG).show();
    }
    //Service 启动时调用
    public int onStartCommand(Intent intent,int flags,int startID){
        System.out.println("Service Started");
        Toast.makeText(this,"Service Started",Toast.LENGTH_LONG).show();
        return  START_STICKY;
    }
    public  void onDestroy(){
        super.onDestroy();
        System.out.println("Service Destroyed");
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_SHORT).show();
    }
}
