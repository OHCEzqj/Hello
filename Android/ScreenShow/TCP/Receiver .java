package ScreenShow;

import javax.swing.*;
import java.awt.*;
import java.awt.image.*;
import com.sun.image.codec.jpeg.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

@SuppressWarnings("serial")
// 播放屏幕录像

public class Receiver extends JFrame {
    BorderLayout borderLayout1 = new BorderLayout();
    Dimension screenSize;
    
	
	
    public Receiver() {
        super();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(screenSize);
        Screen p = new Screen();             //Screen 是一个内部类
        Container c = this.getContentPane();
        c.setLayout(borderLayout1);
        c.add(p, "Center");
        new Thread(p).start();
        this.setVisible(true);
        
    }
    
    public static void main(String[] args) {
        new WnetWScreenRecordPlayer();
    }
    
    class Screen extends JPanel implements Runnable{
    	  private BorderLayout borderLayout1 = new BorderLayout();
    	  private Image cimage;
    	  
    	  ServerSocket ss;
    	  Socket s;
    	  
    	  public void run(){
    		  try {
				recive();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("run()..........");
			}
    	  }
    	  
    	  
    	  

    	  public Image loadImage(String name) {
    		  Toolkit tk = Toolkit.getDefaultToolkit();
    		  Image image = null;
    		  image = tk.getImage("C:\\record2\\" + name);
    		  MediaTracker mt = new MediaTracker(this);
    		  mt.addImage(image, 0);
    		  try {
    			  mt.waitForID(0);
    		  }catch (Exception e) {
    			  e.printStackTrace();
    			  System.out.println(e);
    		  }
    		  return image;
    	  }

    	  public Screen() {
    		  super();
    		  this.setLayout(null);
    		  reciveServer();
    	  }

    	  public void paint(Graphics g){
    		  super.paint(g);
    		  Graphics2D g2 = (Graphics2D) g;
    		  g2.drawImage(cimage, 0, 0, null);
    	  }
    	  
    	  public void recive()throws Exception{
    		  
    		  long i = 0;
    		  
    		  while(true){
    		  	s = ss.accept();
    		  	
    	        File file=new File("C:\\record2\\"+i+".png");
    	        
    	        file.createNewFile();

    	        RandomAccessFile raf=new RandomAccessFile(file,"rw");
    	        
    	        InputStream netIn=s.getInputStream();

    	        InputStream in=new DataInputStream(new BufferedInputStream(netIn));
    	        

    	        //创建缓冲区缓冲网络数据

    	        byte[] buf=new byte[2048];

    	        int num=in.read(buf);     
    	        
    	        	while(num!=(-1)){//是否读完所有数据

    	                raf.write(buf,0,num);//将数据写往文件

    	                raf.skipBytes(num);//顺序写文件字节

    	                num=in.read(buf);//继续从网络中读取文件

    	        	}
    	        	in.close();
    	        	raf.close();
    	        cimage = loadImage(i + ".png");
  				i++;
  				repaint();
  				Thread.sleep(100);//与录像时每秒帧数一致
    	      }
    	  }
    	  public void reciveServer(){
    	    	
    	    	try {
    				ss=new ServerSocket(3011);
    			} catch (IOException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    			
    			
    	    }
    }
}
