package Thread;




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
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Send extends Thread {

    private Dimension screenSize;
    private Rectangle rectangle;
    private Robot robot;
    DatagramSocket datagramSocket;
    String ip=null;
    InetAddress inetAddress=null;
    @SuppressWarnings("unused")
    //private JPEGImageEncoder encoder;

    public  Send(InetAddress inetAddres) {
    	
    	inetAddress=inetAddres;
		System.out.println("address set Success");
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

  

    public void run() {
        FileOutputStream fos = null;
       while (true) {
            try {
                BufferedImage image = robot.createScreenCapture(rectangle);// 捕获制定屏幕矩形区域
                fos = new FileOutputStream("f:\\Records\\1.png");
                
                JPEGCodec.createJPEGEncoder(fos).encode(image);// 图像编码成JPEG
                fos.close();
                System.out.println("图片生成成功");
                Thread.sleep(100);// 每秒25帧
                send3();
                
                //i++;
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("diaoyong"+e);
                try {
                    if (fos != null)
                        fos.close();
                } catch (Exception e1) {
                }
           }
        }
    }
    
   public void send3() throws Exception{
	   
	 
			datagramSocket = new DatagramSocket(8666);
			System.out.println("Socket创建成功");
	
		

	  
		File file = new File("f:\\Records\\1.png");
		FileInputStream fis;
		//创建一个FileInputStream，之后读取字节
		fis = new FileInputStream(file);
		System.out.println("输入流创建成功");
		
		
		byte[] buf = new byte[2048];
		int num; 
			
		System.out.println("即将发送:"+buf.toString());
	 	DatagramPacket datagramPacketn=null;
	 	  while((num=fis.read(buf))!=-1){  
	            //3,创建数据包对象，因为udp协议是需要将数据封装到指定的数据包中。  
	 		 datagramPacketn = new DatagramPacket(buf,num,inetAddress,8666);  
	            //4,使用udpsocket服务的send方法。将数据包发出。  
	             datagramSocket.send(datagramPacketn);  
	             System.out.println("正在发送");
	        }  

		System.out.println("发送成功:"+buf.toString());
		datagramSocket.close();
		fis.close();
		file.delete();
		System.out.println("关闭");
		//}
	}
		
}
