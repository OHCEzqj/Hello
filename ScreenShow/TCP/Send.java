package ScreenShow;


import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import com.sun.image.codec.jpeg.*;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Send extends Thread {

    private Dimension screenSize;
    private Rectangle rectangle;
    private Robot robot;
    private Socket s;
    @SuppressWarnings("unused")
    //private JPEGImageEncoder encoder;

    public  Send() {
    	
    	File ml = new File("f:\\Records\\");
	   	if(!ml.exists()){
	   		if(ml.mkdir()){
	   			System.out.println("文件夹创建成功");
	   		}else {
	   			System.out.println("文件夹创建成失败");
			}
	   		
	   	}else {
	   		System.out.println("文件夹创已经存在");
	   	}
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        rectangle = new Rectangle(screenSize);// 可以指定捕获屏幕区域
        try {
            robot = new Robot();
            System.out.println("Robot创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        new WnetWScreenRecorder().start();
    }

    public void run() {
        FileOutputStream fos = null;
       while (true) {
            try {
                BufferedImage image = robot.createScreenCapture(rectangle);// 捕获制定屏幕矩形区域
                fos = new FileOutputStream("f:\\Records\\1.png");
                
                JPEGCodec.createJPEGEncoder(fos).encode(image);// 图像编码成JPEG
                fos.close();
                System.out.println("图片生成成功");
                Thread.sleep(300);// 每秒25帧
                send3();
                
                //i++;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(e);
                try {
                    if (fos != null)
                        fos.close();
                } catch (Exception e1) {
                }
           }
        }
    }
    
   public void send3() throws Exception{
	   
			s = new Socket("192.168.0.4",8666);
			System.out.println("Socket创建成功");

	   	
		File file = new File("f:\\Records\\1.png");
		FileInputStream fis;
		fis = new FileInputStream(file);
		System.out.println("输入流创建成功");
		DataOutputStream opt = new DataOutputStream(s.getOutputStream());
		System.out.println("输出流成功");
		
//		byte[] buf = new byte[2048];
//		int num;
//		num = fis.read(buf);
//		System.out.println("写入成功");//测地，完成到这
//		while(num!=-1){
//			opt.write(buf,0,num);
//			opt.flush();
//			num = fis.read(buf);
//			System.out.println("fasong 成功");
//		}
		
		int size = fis.available();  
        
        System.out.println("size = "+size);  
        byte[] data = new byte[size];    
        fis.read(data);    
        opt.writeInt(size);    
        opt.write(data);    
          
       
		
		System.out.println("发送成功");
		opt.close();
		fis.close();
		file.delete();
		System.out.println("关闭");
		
		//}
	}
		
}
