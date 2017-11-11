package com.example.zqj.mybroadcast;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        实例化定义好的BroadcastReceiver
        MyReceiver receiver=new MyReceiver();
//        实例化过滤器，并设置过滤的广播的action,
        IntentFilter filter=new IntentFilter("MY_ACTION");
//        注册
        registerReceiver(receiver,filter);

button=(Button)findViewById(R.id.button);
       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent();
//               action需要与注册广播的action一样
               intent.setAction("MY_ACTION");
//               传递的消息
               intent.putExtra("msg","send Broadcast");
//               发送
               sendBroadcast(intent);

           }
       });
    }
}
