package com.example.zqj.pptcontrol.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zqj.pptcontrol.R;
import com.example.zqj.pptcontrol.socket.Constant;
import com.example.zqj.pptcontrol.socket.MySocket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by zqj on 2017/5/18.
 */

public class ScreenShow extends AppCompatActivity {
    private byte cmdBuffer[] = new byte[Constant.bufferSize];
    DatagramSocket datagramSocket = null;
    ImageView imageView;
    byte[] buf = new byte[2048];
    ByteArrayOutputStream baos;
    private MySocket myApplication=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
//        //设置成全屏模式
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//强制为横屏
        setContentView(R.layout.screenshow);
        baos = new ByteArrayOutputStream();
      
        imageView = (ImageView) findViewById(R.id.imageView);


        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               
                Thread  t = new Thread(new MyThread());
                    t.start();


            }
        });

        
    class MyThread implements Runnable {

        @Override
        public void run() {

            Log.i("tag", "run: ");
            while (true) {
                DatagramPacket datagramPacketn = new DatagramPacket(buf, buf.length);


                Log.i("tag", "即将receive: ");

                DataOutputStream out = null;
                //  InputStream in=new DataInputStream();

                while (true) {

                    //3,使用socket对象的receive方法将接收到的数据都存储到数据包的对象中。
                    try {

                        datagramSocket.receive(datagramPacketn);//阻塞式方法。
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //获取数据内容。
                    byte[] data = datagramPacketn.getData();

                    baos.write(datagramPacketn.getData(), 0, datagramPacketn.getLength());
                    if (data.length > datagramPacketn.getLength()) {//结束条件，当数据的长度小于接受数组的长度时，说明已是最后一次，故结束循环
                        break;
                    }
                }//while
                Log.i("tag", "receive: Success");


                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = false;//inJustDecodeBounds 需要设置为false，如果设置为true，那么将返回null

                // final Bitmap bitmap = BitmapFactory.decodeStream(in);
                final Bitmap bitmap = BitmapFactory.decodeByteArray(baos.toByteArray(), 0, baos.toByteArray().length, opts);
                // final Bitmap bitmap = BitmapFactory.decodeStream(in);
                baos.reset();
                imageView.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);
                    }
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
    
   
}