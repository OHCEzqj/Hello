package com.example.zqj.myservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service {
    public MyService() {
    }
  //绑定时调用
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        System.out.println("Service Binded");
//        throw new UnsupportedOperationException("Not yet implemented");
    return  new Binder();
    }
    // System.out 用于测试
    //创建时调用
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
    //关闭时调用
    public  void onDestroy(){
        super.onDestroy();
        System.out.println("Service Destroyed");
        Toast.makeText(this,"Service Destroyed",Toast.LENGTH_SHORT).show();
    }
    //解除绑定时调用
    public boolean onUnbind(Intent intent){
        System.out.println("Service Unbinded");
        return true;

    }

}
